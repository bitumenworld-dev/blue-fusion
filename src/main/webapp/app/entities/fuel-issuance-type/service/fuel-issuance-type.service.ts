import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFuelIssuanceType, NewFuelIssuanceType } from '../fuel-issuance-type.model';

export type PartialUpdateFuelIssuanceType = Partial<IFuelIssuanceType> & Pick<IFuelIssuanceType, 'id'>;

export type EntityResponseType = HttpResponse<IFuelIssuanceType>;
export type EntityArrayResponseType = HttpResponse<IFuelIssuanceType[]>;

@Injectable({ providedIn: 'root' })
export class FuelIssuanceTypeService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fuel-issuance-types');

  create(fuelIssuanceType: NewFuelIssuanceType): Observable<EntityResponseType> {
    return this.http.post<IFuelIssuanceType>(this.resourceUrl, fuelIssuanceType, { observe: 'response' });
  }

  update(fuelIssuanceType: IFuelIssuanceType): Observable<EntityResponseType> {
    return this.http.put<IFuelIssuanceType>(
      `${this.resourceUrl}/${this.getFuelIssuanceTypeIdentifier(fuelIssuanceType)}`,
      fuelIssuanceType,
      { observe: 'response' },
    );
  }

  partialUpdate(fuelIssuanceType: PartialUpdateFuelIssuanceType): Observable<EntityResponseType> {
    return this.http.patch<IFuelIssuanceType>(
      `${this.resourceUrl}/${this.getFuelIssuanceTypeIdentifier(fuelIssuanceType)}`,
      fuelIssuanceType,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFuelIssuanceType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFuelIssuanceType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFuelIssuanceTypeIdentifier(fuelIssuanceType: Pick<IFuelIssuanceType, 'id'>): number {
    return fuelIssuanceType.id;
  }

  compareFuelIssuanceType(o1: Pick<IFuelIssuanceType, 'id'> | null, o2: Pick<IFuelIssuanceType, 'id'> | null): boolean {
    return o1 && o2 ? this.getFuelIssuanceTypeIdentifier(o1) === this.getFuelIssuanceTypeIdentifier(o2) : o1 === o2;
  }

  addFuelIssuanceTypeToCollectionIfMissing<Type extends Pick<IFuelIssuanceType, 'id'>>(
    fuelIssuanceTypeCollection: Type[],
    ...fuelIssuanceTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fuelIssuanceTypes: Type[] = fuelIssuanceTypesToCheck.filter(isPresent);
    if (fuelIssuanceTypes.length > 0) {
      const fuelIssuanceTypeCollectionIdentifiers = fuelIssuanceTypeCollection.map(fuelIssuanceTypeItem =>
        this.getFuelIssuanceTypeIdentifier(fuelIssuanceTypeItem),
      );
      const fuelIssuanceTypesToAdd = fuelIssuanceTypes.filter(fuelIssuanceTypeItem => {
        const fuelIssuanceTypeIdentifier = this.getFuelIssuanceTypeIdentifier(fuelIssuanceTypeItem);
        if (fuelIssuanceTypeCollectionIdentifiers.includes(fuelIssuanceTypeIdentifier)) {
          return false;
        }
        fuelIssuanceTypeCollectionIdentifiers.push(fuelIssuanceTypeIdentifier);
        return true;
      });
      return [...fuelIssuanceTypesToAdd, ...fuelIssuanceTypeCollection];
    }
    return fuelIssuanceTypeCollection;
  }
}
