import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IWorkshop, NewWorkshop } from '../workshop.model';

export type PartialUpdateWorkshop = Partial<IWorkshop> & Pick<IWorkshop, 'id'>;

export type EntityResponseType = HttpResponse<IWorkshop>;
export type EntityArrayResponseType = HttpResponse<IWorkshop[]>;

@Injectable({ providedIn: 'root' })
export class WorkshopService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/workshops');

  create(workshop: NewWorkshop): Observable<EntityResponseType> {
    return this.http.post<IWorkshop>(this.resourceUrl, workshop, { observe: 'response' });
  }

  update(workshop: IWorkshop): Observable<EntityResponseType> {
    return this.http.put<IWorkshop>(`${this.resourceUrl}/${this.getWorkshopIdentifier(workshop)}`, workshop, { observe: 'response' });
  }

  partialUpdate(workshop: PartialUpdateWorkshop): Observable<EntityResponseType> {
    return this.http.patch<IWorkshop>(`${this.resourceUrl}/${this.getWorkshopIdentifier(workshop)}`, workshop, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWorkshop>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWorkshop[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getWorkshopIdentifier(workshop: Pick<IWorkshop, 'id'>): number {
    return workshop.id;
  }

  compareWorkshop(o1: Pick<IWorkshop, 'id'> | null, o2: Pick<IWorkshop, 'id'> | null): boolean {
    return o1 && o2 ? this.getWorkshopIdentifier(o1) === this.getWorkshopIdentifier(o2) : o1 === o2;
  }

  addWorkshopToCollectionIfMissing<Type extends Pick<IWorkshop, 'id'>>(
    workshopCollection: Type[],
    ...workshopsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const workshops: Type[] = workshopsToCheck.filter(isPresent);
    if (workshops.length > 0) {
      const workshopCollectionIdentifiers = workshopCollection.map(workshopItem => this.getWorkshopIdentifier(workshopItem));
      const workshopsToAdd = workshops.filter(workshopItem => {
        const workshopIdentifier = this.getWorkshopIdentifier(workshopItem);
        if (workshopCollectionIdentifiers.includes(workshopIdentifier)) {
          return false;
        }
        workshopCollectionIdentifiers.push(workshopIdentifier);
        return true;
      });
      return [...workshopsToAdd, ...workshopCollection];
    }
    return workshopCollection;
  }
}
