import { ChangeDetectionStrategy, Component, computed, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { Store } from '@ngrx/store';
import { ConfigurationItem } from '../configuration-item.model';
import { configurationItemFeature } from '../state/configuration-item.reducer';
import { ConfigurationItemActions } from '../state/configuration-item.actions';

interface FlatConfigurationItem extends ConfigurationItem {
  level: number;
  isExpanded?: boolean;
  hasChildren?: boolean;
}

@Component({
  selector: 'app-configuration-overview',
  imports: [
    CommonModule,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    FormsModule
  ],
  template: `
    <div class="container">
      <h1>Configuration Items Overview</h1>

      <mat-form-field appearance="outline" class="filter-field">
        <mat-label>Filter</mat-label>
        <input matInput (keyup)="applyFilter($event)" placeholder="Search CI..." #input>
      </mat-form-field>

      <table mat-table [dataSource]="filteredDataSource()" class="mat-elevation-z8">
        <!-- Name Column -->
        <ng-container matColumnDef="name">
          <th mat-header-cell *mat-header-cell> Name </th>
          <td mat-cell *mat-cell="let element" [style.padding-left.px]="element.level * 24">
            <div class="name-cell">
              @if (element.hasChildren) {
                <button mat-icon-button (click)="toggle(element)" class="toggle-button">
                  <mat-icon>{{ element.isExpanded ? 'expand_more' : 'chevron_right' }}</mat-icon>
                </button>
              } @else {
                <span class="spacer"></span>
              }

              @if (editingId() === element.id) {
                <mat-form-field appearance="outline" class="edit-field">
                  <input matInput [(ngModel)]="editName" (keyup.enter)="save(element)" (blur)="cancelEdit()">
                </mat-form-field>
              } @else {
                <span class="ci-name" (click)="startEdit(element)">{{ element.name }}</span>
              }
            </div>
          </td>
        </ng-container>

        <!-- Type Column -->
        <ng-container matColumnDef="type">
          <th mat-header-cell *mat-header-cell> Type </th>
          <td mat-cell *mat-cell="let element"> {{ element.type }} </td>
        </ng-container>

        <!-- Start Date Column -->
        <ng-container matColumnDef="startDate">
          <th mat-header-cell *mat-header-cell> Start Date </th>
          <td mat-cell *mat-cell="let element"> {{ element.startDate }} </td>
        </ng-container>

        <!-- End Date Column -->
        <ng-container matColumnDef="endDate">
          <th mat-header-cell *mat-header-cell> End Date </th>
          <td mat-cell *mat-cell="let element"> {{ element.endDate || '-' }} </td>
        </ng-container>

        <tr mat-header-row *mat-header-rowDef="displayedColumns"></tr>
        <tr mat-row *mat-rowDef="let row; columns: displayedColumns;"></tr>

        <!-- Row shown when there is no matching data. -->
        <tr class="mat-row" *mat-noDataRow>
          <td class="mat-cell" colspan="4">No data matching the filter "{{input.value}}"</td>
        </tr>
      </table>
    </div>
  `,
  styles: [`
    .container { padding: 24px; }
    .filter-field { width: 100%; margin-bottom: 16px; }
    table { width: 100%; }
    .name-cell { display: flex; align-items: center; }
    .toggle-button { width: 40px; height: 40px; margin-right: 4px; }
    .spacer { width: 44px; display: inline-block; }
    .ci-name { cursor: pointer; padding: 4px 8px; border-radius: 4px; }
    .ci-name:hover { background-color: rgba(0,0,0,0.04); }
    .edit-field { margin-top: 16px; margin-bottom: -16px; }
  `],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ConfigurationOverviewComponent implements OnInit {
  private readonly store = inject(Store);

  protected readonly items = this.store.selectSignal(configurationItemFeature.selectItems);
  protected readonly displayedColumns = ['name', 'type', 'startDate', 'endDate'];

  private readonly expandedIds = signal<Set<string>>(new Set());
  protected readonly filterText = signal<string>('');

  protected readonly editingId = signal<string | null>(null);
  protected editName = '';

  protected readonly flatData = computed(() => {
    const rawItems = this.items();
    const expanded = this.expandedIds();
    const result: FlatConfigurationItem[] = [];

    const flatten = (items: ConfigurationItem[], level: number) => {
      items.forEach(item => {
        const hasChildren = !!item.children?.length;
        const isExpanded = expanded.has(item.id);

        result.push({
          ...item,
          level,
          hasChildren,
          isExpanded
        });

        if (hasChildren && isExpanded) {
          flatten(item.children!, level + 1);
        }
      });
    };

    flatten(rawItems, 0);
    return result;
  });

  protected readonly filteredDataSource = computed(() => {
    const data = this.flatData();
    const filter = this.filterText().toLowerCase();

    if (!filter) return data;

    return data.filter(item =>
      item.name.toLowerCase().includes(filter) ||
      item.type.toLowerCase().includes(filter) ||
      item.id.toLowerCase().includes(filter)
    );
  });

  ngOnInit() {
    this.store.dispatch(ConfigurationItemActions.loadOverview());
  }

  protected toggle(element: FlatConfigurationItem) {
    this.expandedIds.update(set => {
      const newSet = new Set(set);
      if (newSet.has(element.id)) {
        newSet.delete(element.id);
      } else {
        newSet.add(element.id);
      }
      return newSet;
    });
  }

  protected applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.filterText.set(filterValue.trim().toLowerCase());
  }

  protected startEdit(element: FlatConfigurationItem) {
    this.editingId.set(element.id);
    this.editName = element.name;
  }

  protected cancelEdit() {
    this.editingId.set(null);
  }

  protected save(element: FlatConfigurationItem) {
    if (this.editName && this.editName !== element.name) {
      this.store.dispatch(ConfigurationItemActions.updateName({
        id: element.id,
        name: this.editName
      }));
    }
    this.editingId.set(null);
  }
}
