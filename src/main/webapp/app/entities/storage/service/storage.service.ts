import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Storage, NewStorage } from '../storage.model';

export type EntityResponseType = HttpResponse<Storage>;
export type EntityArrayResponseType = HttpResponse<Storage[]>;
export type StorageEntityArrayResponseType = HttpResponse<Storage[]>;

@Injectable({ providedIn: 'root' })
export class StorageService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/storage');

  getStorageIdentifier(storage: Pick<Storage, 'id'>): number {
    return storage.id;
  }

  create(storage: NewStorage): Observable<EntityResponseType> {
    return this.http.post<Storage>(this.resourceUrl, storage, { observe: 'response' });
  }

  update(storage: Storage): Observable<EntityResponseType> {
    return this.http.put<Storage>(`${this.resourceUrl}/${this.getStorageIdentifier(storage)}`, storage, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<Storage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<Storage[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
