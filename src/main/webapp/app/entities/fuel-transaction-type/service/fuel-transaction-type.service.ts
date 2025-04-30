import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFuelTransactionType, NewFuelTransactionType } from '../fuel-transaction-type.model';

export type PartialUpdateFuelTransactionType = Partial<IFuelTransactionType> & Pick<IFuelTransactionType, 'id'>;

export type EntityResponseType = HttpResponse<IFuelTransactionType>;
export type EntityArrayResponseType = HttpResponse<IFuelTransactionType[]>;

@Injectable({ providedIn: 'root' })
export class FuelTransactionTypeService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fuel-transaction-types');

  create(fuelTransactionType: NewFuelTransactionType): Observable<EntityResponseType> {
    return this.http.post<IFuelTransactionType>(this.resourceUrl, fuelTransactionType, { observe: 'response' });
  }

  update(fuelTransactionType: IFuelTransactionType): Observable<EntityResponseType> {
    return this.http.put<IFuelTransactionType>(
      `${this.resourceUrl}/${this.getFuelTransactionTypeIdentifier(fuelTransactionType)}`,
      fuelTransactionType,
      { observe: 'response' },
    );
  }

  partialUpdate(fuelTransactionType: PartialUpdateFuelTransactionType): Observable<EntityResponseType> {
    return this.http.patch<IFuelTransactionType>(
      `${this.resourceUrl}/${this.getFuelTransactionTypeIdentifier(fuelTransactionType)}`,
      fuelTransactionType,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFuelTransactionType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFuelTransactionType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFuelTransactionTypeIdentifier(fuelTransactionType: Pick<IFuelTransactionType, 'id'>): number {
    return fuelTransactionType.id;
  }

  compareFuelTransactionType(o1: Pick<IFuelTransactionType, 'id'> | null, o2: Pick<IFuelTransactionType, 'id'> | null): boolean {
    return o1 && o2 ? this.getFuelTransactionTypeIdentifier(o1) === this.getFuelTransactionTypeIdentifier(o2) : o1 === o2;
  }

  addFuelTransactionTypeToCollectionIfMissing<Type extends Pick<IFuelTransactionType, 'id'>>(
    fuelTransactionTypeCollection: Type[],
    ...fuelTransactionTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fuelTransactionTypes: Type[] = fuelTransactionTypesToCheck.filter(isPresent);
    if (fuelTransactionTypes.length > 0) {
      const fuelTransactionTypeCollectionIdentifiers = fuelTransactionTypeCollection.map(fuelTransactionTypeItem =>
        this.getFuelTransactionTypeIdentifier(fuelTransactionTypeItem),
      );
      const fuelTransactionTypesToAdd = fuelTransactionTypes.filter(fuelTransactionTypeItem => {
        const fuelTransactionTypeIdentifier = this.getFuelTransactionTypeIdentifier(fuelTransactionTypeItem);
        if (fuelTransactionTypeCollectionIdentifiers.includes(fuelTransactionTypeIdentifier)) {
          return false;
        }
        fuelTransactionTypeCollectionIdentifiers.push(fuelTransactionTypeIdentifier);
        return true;
      });
      return [...fuelTransactionTypesToAdd, ...fuelTransactionTypeCollection];
    }
    return fuelTransactionTypeCollection;
  }
}
