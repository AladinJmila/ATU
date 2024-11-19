import { Routes } from '@angular/router';
import { CountyComponent } from './county/county.component';
import { CountyDetailsComponent } from './county-details/county-details.component';

export const routes: Routes = [
  { path: 'county', component: CountyComponent },
  { path: '', redirectTo: 'county', pathMatch: 'full' },
];
