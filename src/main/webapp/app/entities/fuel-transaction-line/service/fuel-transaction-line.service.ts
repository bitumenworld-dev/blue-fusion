import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFuelTransactionLine, NewFuelTransactionLine } from '../fuel-transaction-line.model';

export type PartialUpdateFuelTransactionLine = Partial<IFuelTransactionLine> & Pick<IFuelTransactionLine, 'id'>;

export type EntityResponseType = HttpResponse<IFuelTransactionLine>;
export type EntityArrayResponseType = HttpResponse<IFuelTransactionLine[]>;

@Injectable({ providedIn: 'root' })
export class FuelTransactionLineService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fuel-transaction-lines');

  create(fuelTransactionLine: NewFuelTransactionLine): Observable<EntityResponseType> {
    return this.http.post<IFuelTransactionLine>(this.resourceUrl, fuelTransactionLine, { observe: 'response' });
  }

  update(fuelTransactionLine: IFuelTransactionLine): Observable<EntityResponseType> {
    return this.http.put<IFuelTransactionLine>(
      `${this.resourceUrl}/${this.getFuelTransactionLineIdentifier(fuelTransactionLine)}`,
      fuelTransactionLine,
      { observe: 'response' },
    );
  }

  partialUpdate(fuelTransactionLine: PartialUpdateFuelTransactionLine): Observable<EntityResponseType> {
    return this.http.patch<IFuelTransactionLine>(
      `${this.resourceUrl}/${this.getFuelTransactionLineIdentifier(fuelTransactionLine)}`,
      fuelTransactionLine,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFuelTransactionLine>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFuelTransactionLine[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFuelTransactionLineIdentifier(fuelTransactionLine: Pick<IFuelTransactionLine, 'id'>): number {
    return fuelTransactionLine.id;
  }

  compareFuelTransactionLine(o1: Pick<IFuelTransactionLine, 'id'> | null, o2: Pick<IFuelTransactionLine, 'id'> | null): boolean {
    return o1 && o2 ? this.getFuelTransactionLineIdentifier(o1) === this.getFuelTransactionLineIdentifier(o2) : o1 === o2;
  }

  addFuelTransactionLineToCollectionIfMissing<Type extends Pick<IFuelTransactionLine, 'id'>>(
    fuelTransactionLineCollection: Type[],
    ...fuelTransactionLinesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fuelTransactionLines: Type[] = fuelTransactionLinesToCheck.filter(isPresent);
    if (fuelTransactionLines.length > 0) {
      const fuelTransactionLineCollectionIdentifiers = fuelTransactionLineCollection.map(fuelTransactionLineItem =>
        this.getFuelTransactionLineIdentifier(fuelTransactionLineItem),
      );
      const fuelTransactionLinesToAdd = fuelTransactionLines.filter(fuelTransactionLineItem => {
        const fuelTransactionLineIdentifier = this.getFuelTransactionLineIdentifier(fuelTransactionLineItem);
        if (fuelTransactionLineCollectionIdentifiers.includes(fuelTransactionLineIdentifier)) {
          return false;
        }
        fuelTransactionLineCollectionIdentifiers.push(fuelTransactionLineIdentifier);
        return true;
      });
      return [...fuelTransactionLinesToAdd, ...fuelTransactionLineCollection];
    }
    return fuelTransactionLineCollection;
  }
}
