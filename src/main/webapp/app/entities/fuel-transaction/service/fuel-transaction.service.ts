import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { FuelTransaction, NewFuelTransaction } from '../fuel-transaction.model';

export type PartialUpdateFuelTransactionHeader = Partial<FuelTransaction> & Pick<FuelTransaction, 'id'>;

export type EntityResponseType = HttpResponse<FuelTransaction>;
export type EntityArrayResponseType = HttpResponse<FuelTransaction[]>;

@Injectable({ providedIn: 'root' })
export class FuelTransactionService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fuel-transaction-headers');

  create(fuelTransactionHeader: NewFuelTransaction): Observable<EntityResponseType> {
    return this.http.post<FuelTransaction>(this.resourceUrl, fuelTransactionHeader, { observe: 'response' });
  }

  update(fuelTransactionHeader: FuelTransaction): Observable<EntityResponseType> {
    return this.http.put<FuelTransaction>(
      `${this.resourceUrl}/${this.getFuelTransactionHeaderIdentifier(fuelTransactionHeader)}`,
      fuelTransactionHeader,
      { observe: 'response' },
    );
  }

  partialUpdate(fuelTransactionHeader: PartialUpdateFuelTransactionHeader): Observable<EntityResponseType> {
    return this.http.patch<FuelTransaction>(
      `${this.resourceUrl}/${this.getFuelTransactionHeaderIdentifier(fuelTransactionHeader)}`,
      fuelTransactionHeader,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<FuelTransaction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<FuelTransaction[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFuelTransactionHeaderIdentifier(fuelTransactionHeader: Pick<FuelTransaction, 'id'>): number {
    return fuelTransactionHeader.id;
  }

  compareFuelTransactionHeader(o1: Pick<FuelTransaction, 'id'> | null, o2: Pick<FuelTransaction, 'id'> | null): boolean {
    return o1 && o2 ? this.getFuelTransactionHeaderIdentifier(o1) === this.getFuelTransactionHeaderIdentifier(o2) : o1 === o2;
  }

  addFuelTransactionHeaderToCollectionIfMissing<Type extends Pick<FuelTransaction, 'id'>>(
    fuelTransactionHeaderCollection: Type[],
    ...fuelTransactionHeadersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fuelTransactionHeaders: Type[] = fuelTransactionHeadersToCheck.filter(isPresent);
    if (fuelTransactionHeaders.length > 0) {
      const fuelTransactionHeaderCollectionIdentifiers = fuelTransactionHeaderCollection.map(fuelTransactionHeaderItem =>
        this.getFuelTransactionHeaderIdentifier(fuelTransactionHeaderItem),
      );
      const fuelTransactionHeadersToAdd = fuelTransactionHeaders.filter(fuelTransactionHeaderItem => {
        const fuelTransactionHeaderIdentifier = this.getFuelTransactionHeaderIdentifier(fuelTransactionHeaderItem);
        if (fuelTransactionHeaderCollectionIdentifiers.includes(fuelTransactionHeaderIdentifier)) {
          return false;
        }
        fuelTransactionHeaderCollectionIdentifiers.push(fuelTransactionHeaderIdentifier);
        return true;
      });
      return [...fuelTransactionHeadersToAdd, ...fuelTransactionHeaderCollection];
    }
    return fuelTransactionHeaderCollection;
  }
}
