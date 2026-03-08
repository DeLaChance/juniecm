import { Routes } from '@angular/router';
import { ConfigurationOverviewComponent } from './configuration-item/components/configuration-overview.component';

export const routes: Routes = [
  { path: 'overview', component: ConfigurationOverviewComponent },
  { path: '', redirectTo: 'overview', pathMatch: 'full' }
];
