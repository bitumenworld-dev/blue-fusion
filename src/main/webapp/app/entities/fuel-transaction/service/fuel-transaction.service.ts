import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { FuelTransaction, NewFuelTransaction } from '../fuel-transaction.model';
import { StorageUnitTransactions } from '../storage-unit.model';

export type FuelTransactionResponseType = HttpResponse<FuelTransaction>;
export type FuelTransactionArrayResponseType = HttpResponse<FuelTransaction[]>;
export type StorageUnitTransactionsResponseType = HttpResponse<StorageUnitTransactions>;

@Injectable({ providedIn: 'root' })
export class FuelTransactionService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('/api/fuel-transaction');

  create(fuelTransaction: NewFuelTransaction): Observable<FuelTransactionResponseType> {
    return this.http.post<FuelTransaction>(this.resourceUrl, fuelTransaction, { observe: 'response' });
  }

  update(fuelTransaction: FuelTransaction): Observable<FuelTransactionResponseType> {
    return this.http.put<FuelTransaction>(
      `${this.resourceUrl}/${this.getFuelTransactionHeaderIdentifier(fuelTransaction)}`,
      fuelTransaction,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<FuelTransactionResponseType> {
    return this.http.get<FuelTransaction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<StorageUnitTransactionsResponseType> {
    const options = createRequestOption(req);
    return this.http.get<StorageUnitTransactions>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFuelTransactionHeaderIdentifier(fuelTransactionHeader: Pick<FuelTransaction, 'fuelTransactionId'>): number {
    return fuelTransactionHeader.fuelTransactionId;
  }

  compareFuelTransactionHeader(
    o1: Pick<FuelTransaction, 'fuelTransactionId'> | null,
    o2: Pick<FuelTransaction, 'fuelTransactionId'> | null,
  ): boolean {
    return o1 && o2 ? this.getFuelTransactionHeaderIdentifier(o1) === this.getFuelTransactionHeaderIdentifier(o2) : o1 === o2;
  }

  addFuelTransactionHeaderToCollectionIfMissing<Type extends Pick<FuelTransaction, 'fuelTransactionId'>>(
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
