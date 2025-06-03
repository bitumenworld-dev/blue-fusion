import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { AssetPlant, NewAssetPlant } from '../asset-plant.model';

export type PartialUpdateAssetPlant = Partial<AssetPlant> & Pick<AssetPlant, 'assetPlantId'>;

export type AssetPlantResponseType = HttpResponse<AssetPlant>;
export type AssetPlantArrayResponseType = HttpResponse<AssetPlant[]>;

@Injectable({ providedIn: 'root' })
export class AssetPlantService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/asset-plants');

  create(assetPlant: NewAssetPlant): Observable<AssetPlantResponseType> {
    return this.http.post<AssetPlant>(this.resourceUrl, assetPlant, { observe: 'response' });
  }

  update(assetPlant: AssetPlant): Observable<AssetPlantResponseType> {
    return this.http.put<AssetPlant>(`${this.resourceUrl}/${this.getAssetPlantIdentifier(assetPlant)}`, assetPlant, {
      observe: 'response',
    });
  }

  partialUpdate(assetPlant: PartialUpdateAssetPlant): Observable<AssetPlantResponseType> {
    return this.http.patch<AssetPlant>(`${this.resourceUrl}/${this.getAssetPlantIdentifier(assetPlant)}`, assetPlant, {
      observe: 'response',
    });
  }

  find(id: number): Observable<AssetPlantResponseType> {
    return this.http.get<AssetPlant>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<AssetPlantArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<AssetPlant[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAssetPlantIdentifier(assetPlant: Pick<AssetPlant, 'assetPlantId'>): number {
    return assetPlant.assetPlantId;
  }

  compareAssetPlant(o1: Pick<AssetPlant, 'assetPlantId'> | null, o2: Pick<AssetPlant, 'assetPlantId'> | null): boolean {
    return o1 && o2 ? this.getAssetPlantIdentifier(o1) === this.getAssetPlantIdentifier(o2) : o1 === o2;
  }

  addAssetPlantToCollectionIfMissing<Type extends Pick<AssetPlant, 'assetPlantId'>>(
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
