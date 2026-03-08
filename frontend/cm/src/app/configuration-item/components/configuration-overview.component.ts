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
  templateUrl: './configuration-overview.component.html',
  styleUrl: './configuration-overview.component.scss',
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
