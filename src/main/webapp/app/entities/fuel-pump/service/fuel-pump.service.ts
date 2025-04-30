import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFuelPump, NewFuelPump } from '../fuel-pump.model';

export type PartialUpdateFuelPump = Partial<IFuelPump> & Pick<IFuelPump, 'id'>;

export type EntityResponseType = HttpResponse<IFuelPump>;
export type EntityArrayResponseType = HttpResponse<IFuelPump[]>;

@Injectable({ providedIn: 'root' })
export class FuelPumpService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fuel-pumps');

  create(fuelPump: NewFuelPump): Observable<EntityResponseType> {
    return this.http.post<IFuelPump>(this.resourceUrl, fuelPump, { observe: 'response' });
  }

  update(fuelPump: IFuelPump): Observable<EntityResponseType> {
    return this.http.put<IFuelPump>(`${this.resourceUrl}/${this.getFuelPumpIdentifier(fuelPump)}`, fuelPump, { observe: 'response' });
  }

  partialUpdate(fuelPump: PartialUpdateFuelPump): Observable<EntityResponseType> {
    return this.http.patch<IFuelPump>(`${this.resourceUrl}/${this.getFuelPumpIdentifier(fuelPump)}`, fuelPump, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFuelPump>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFuelPump[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFuelPumpIdentifier(fuelPump: Pick<IFuelPump, 'id'>): number {
    return fuelPump.id;
  }

  compareFuelPump(o1: Pick<IFuelPump, 'id'> | null, o2: Pick<IFuelPump, 'id'> | null): boolean {
    return o1 && o2 ? this.getFuelPumpIdentifier(o1) === this.getFuelPumpIdentifier(o2) : o1 === o2;
  }

  addFuelPumpToCollectionIfMissing<Type extends Pick<IFuelPump, 'id'>>(
    fuelPumpCollection: Type[],
    ...fuelPumpsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fuelPumps: Type[] = fuelPumpsToCheck.filter(isPresent);
    if (fuelPumps.length > 0) {
      const fuelPumpCollectionIdentifiers = fuelPumpCollection.map(fuelPumpItem => this.getFuelPumpIdentifier(fuelPumpItem));
      const fuelPumpsToAdd = fuelPumps.filter(fuelPumpItem => {
        const fuelPumpIdentifier = this.getFuelPumpIdentifier(fuelPumpItem);
        if (fuelPumpCollectionIdentifiers.includes(fuelPumpIdentifier)) {
          return false;
        }
        fuelPumpCollectionIdentifiers.push(fuelPumpIdentifier);
        return true;
      });
      return [...fuelPumpsToAdd, ...fuelPumpCollection];
    }
    return fuelPumpCollection;
  }
}
