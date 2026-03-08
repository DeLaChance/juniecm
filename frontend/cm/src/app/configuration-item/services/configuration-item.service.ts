import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigurationItem } from '../configuration-item.model';

@Injectable({
  providedIn: 'root'
})
export class ConfigurationItemService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = '/api/v1/configuration_items';

  getOverview(): Observable<ConfigurationItem[]> {
    return this.http.get<ConfigurationItem[]>(`${this.apiUrl}/overview`);
  }

  updateName(id: string, name: string): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${id}`, { name });
  }
}
