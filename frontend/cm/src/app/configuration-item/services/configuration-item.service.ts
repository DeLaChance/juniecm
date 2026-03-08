import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { ConfigurationItemResponse } from '../configuration-item.model';

@Injectable({
  providedIn: 'root'
})
export class ConfigurationItemService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = '/api/v1/configuration_items';

  getOverview(): Observable<ConfigurationItemResponse> {
    return this.http.get<ConfigurationItemResponse>(`${this.apiUrl}/overview`).pipe(
      map(response => response)
    );
  }

  updateName(id: string, name: string): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${id}`, { name });
  }

  // Your constant variable
  private CONFIG_DATA: ConfigurationItemResponse = {
    "data": [
      {
        "id": "ABC01",
        "name": "Laptop Lucien",
        "type": "hardware",
        "startDate": "2026-03-01T08:32:01",
        "endDate": null,
        "children": [
          {
            "id": "ABC02",
            "name": "Windows License",
            "type": "software",
            "startDate": "2026-03-01T08:32:01",
            "endDate": null,
            "children": []
          }
        ]
      },
      {
        "id": "ABC03",
        "name": "MacBook Alice",
        "type": "hardware",
        "startDate": "2026-01-15T10:00:00",
        "endDate": null,
        "children": []
      }
    ],
    "totalCount": 3
  };
}
