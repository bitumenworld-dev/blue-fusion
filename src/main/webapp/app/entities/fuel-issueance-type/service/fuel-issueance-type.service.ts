import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFuelIssueanceType, NewFuelIssueanceType } from '../fuel-issueance-type.model';

export type PartialUpdateFuelIssueanceType = Partial<IFuelIssueanceType> & Pick<IFuelIssueanceType, 'id'>;

export type EntityResponseType = HttpResponse<IFuelIssueanceType>;
export type EntityArrayResponseType = HttpResponse<IFuelIssueanceType[]>;

@Injectable({ providedIn: 'root' })
export class FuelIssueanceTypeService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fuel-issueance-types');

  create(fuelIssueanceType: NewFuelIssueanceType): Observable<EntityResponseType> {
    return this.http.post<IFuelIssueanceType>(this.resourceUrl, fuelIssueanceType, { observe: 'response' });
  }

  update(fuelIssueanceType: IFuelIssueanceType): Observable<EntityResponseType> {
    return this.http.put<IFuelIssueanceType>(
      `${this.resourceUrl}/${this.getFuelIssueanceTypeIdentifier(fuelIssueanceType)}`,
      fuelIssueanceType,
      { observe: 'response' },
    );
  }

  partialUpdate(fuelIssueanceType: PartialUpdateFuelIssueanceType): Observable<EntityResponseType> {
    return this.http.patch<IFuelIssueanceType>(
      `${this.resourceUrl}/${this.getFuelIssueanceTypeIdentifier(fuelIssueanceType)}`,
      fuelIssueanceType,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFuelIssueanceType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFuelIssueanceType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFuelIssueanceTypeIdentifier(fuelIssueanceType: Pick<IFuelIssueanceType, 'id'>): number {
    return fuelIssueanceType.id;
  }

  compareFuelIssueanceType(o1: Pick<IFuelIssueanceType, 'id'> | null, o2: Pick<IFuelIssueanceType, 'id'> | null): boolean {
    return o1 && o2 ? this.getFuelIssueanceTypeIdentifier(o1) === this.getFuelIssueanceTypeIdentifier(o2) : o1 === o2;
  }

  addFuelIssueanceTypeToCollectionIfMissing<Type extends Pick<IFuelIssueanceType, 'id'>>(
    fuelIssueanceTypeCollection: Type[],
    ...fuelIssueanceTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fuelIssueanceTypes: Type[] = fuelIssueanceTypesToCheck.filter(isPresent);
    if (fuelIssueanceTypes.length > 0) {
      const fuelIssueanceTypeCollectionIdentifiers = fuelIssueanceTypeCollection.map(fuelIssueanceTypeItem =>
        this.getFuelIssueanceTypeIdentifier(fuelIssueanceTypeItem),
      );
      const fuelIssueanceTypesToAdd = fuelIssueanceTypes.filter(fuelIssueanceTypeItem => {
        const fuelIssueanceTypeIdentifier = this.getFuelIssueanceTypeIdentifier(fuelIssueanceTypeItem);
        if (fuelIssueanceTypeCollectionIdentifiers.includes(fuelIssueanceTypeIdentifier)) {
          return false;
        }
        fuelIssueanceTypeCollectionIdentifiers.push(fuelIssueanceTypeIdentifier);
        return true;
      });
      return [...fuelIssueanceTypesToAdd, ...fuelIssueanceTypeCollection];
    }
    return fuelIssueanceTypeCollection;
  }
}
