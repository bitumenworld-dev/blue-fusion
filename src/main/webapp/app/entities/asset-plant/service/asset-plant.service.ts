import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAssetPlant, NewAssetPlant } from '../asset-plant.model';

export type PartialUpdateAssetPlant = Partial<IAssetPlant> & Pick<IAssetPlant, 'id'>;

export type EntityResponseType = HttpResponse<IAssetPlant>;
export type EntityArrayResponseType = HttpResponse<IAssetPlant[]>;

@Injectable({ providedIn: 'root' })
export class AssetPlantService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/asset-plants');

  create(assetPlant: NewAssetPlant): Observable<EntityResponseType> {
    return this.http.post<IAssetPlant>(this.resourceUrl, assetPlant, { observe: 'response' });
  }

  update(assetPlant: IAssetPlant): Observable<EntityResponseType> {
    return this.http.put<IAssetPlant>(`${this.resourceUrl}/${this.getAssetPlantIdentifier(assetPlant)}`, assetPlant, {
      observe: 'response',
    });
  }

  partialUpdate(assetPlant: PartialUpdateAssetPlant): Observable<EntityResponseType> {
    return this.http.patch<IAssetPlant>(`${this.resourceUrl}/${this.getAssetPlantIdentifier(assetPlant)}`, assetPlant, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAssetPlant>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAssetPlant[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAssetPlantIdentifier(assetPlant: Pick<IAssetPlant, 'id'>): number {
    return assetPlant.id;
  }

  compareAssetPlant(o1: Pick<IAssetPlant, 'id'> | null, o2: Pick<IAssetPlant, 'id'> | null): boolean {
    return o1 && o2 ? this.getAssetPlantIdentifier(o1) === this.getAssetPlantIdentifier(o2) : o1 === o2;
  }

  addAssetPlantToCollectionIfMissing<Type extends Pick<IAssetPlant, 'id'>>(
    assetPlantCollection: Type[],
    ...assetPlantsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const assetPlants: Type[] = assetPlantsToCheck.filter(isPresent);
    if (assetPlants.length > 0) {
      const assetPlantCollectionIdentifiers = assetPlantCollection.map(assetPlantItem => this.getAssetPlantIdentifier(assetPlantItem));
      const assetPlantsToAdd = assetPlants.filter(assetPlantItem => {
        const assetPlantIdentifier = this.getAssetPlantIdentifier(assetPlantItem);
        if (assetPlantCollectionIdentifiers.includes(assetPlantIdentifier)) {
          return false;
        }
        assetPlantCollectionIdentifiers.push(assetPlantIdentifier);
        return true;
      });
      return [...assetPlantsToAdd, ...assetPlantCollection];
    }
    return assetPlantCollection;
  }
}
