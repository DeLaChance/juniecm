import { Component, signal } from '@angular/core';
import { ConfigurationOverviewComponent } from './configuration-item/components/configuration-overview.component';

@Component({
  selector: 'app-root',
  imports: [ConfigurationOverviewComponent],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('cm');
}
