import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAssetPlantPhoto, NewAssetPlantPhoto } from '../asset-plant-photo.model';

export type PartialUpdateAssetPlantPhoto = Partial<IAssetPlantPhoto> & Pick<IAssetPlantPhoto, 'id'>;

export type EntityResponseType = HttpResponse<IAssetPlantPhoto>;
export type EntityArrayResponseType = HttpResponse<IAssetPlantPhoto[]>;

@Injectable({ providedIn: 'root' })
export class AssetPlantPhotoService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/asset-plant-photos');

  create(assetPlantPhoto: NewAssetPlantPhoto): Observable<EntityResponseType> {
    return this.http.post<IAssetPlantPhoto>(this.resourceUrl, assetPlantPhoto, { observe: 'response' });
  }

  update(assetPlantPhoto: IAssetPlantPhoto): Observable<EntityResponseType> {
    return this.http.put<IAssetPlantPhoto>(`${this.resourceUrl}/${this.getAssetPlantPhotoIdentifier(assetPlantPhoto)}`, assetPlantPhoto, {
      observe: 'response',
    });
  }

  partialUpdate(assetPlantPhoto: PartialUpdateAssetPlantPhoto): Observable<EntityResponseType> {
    return this.http.patch<IAssetPlantPhoto>(`${this.resourceUrl}/${this.getAssetPlantPhotoIdentifier(assetPlantPhoto)}`, assetPlantPhoto, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAssetPlantPhoto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAssetPlantPhoto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAssetPlantPhotoIdentifier(assetPlantPhoto: Pick<IAssetPlantPhoto, 'id'>): number {
    return assetPlantPhoto.id;
  }

  compareAssetPlantPhoto(o1: Pick<IAssetPlantPhoto, 'id'> | null, o2: Pick<IAssetPlantPhoto, 'id'> | null): boolean {
    return o1 && o2 ? this.getAssetPlantPhotoIdentifier(o1) === this.getAssetPlantPhotoIdentifier(o2) : o1 === o2;
  }

  addAssetPlantPhotoToCollectionIfMissing<Type extends Pick<IAssetPlantPhoto, 'id'>>(
    assetPlantPhotoCollection: Type[],
    ...assetPlantPhotosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const assetPlantPhotos: Type[] = assetPlantPhotosToCheck.filter(isPresent);
    if (assetPlantPhotos.length > 0) {
      const assetPlantPhotoCollectionIdentifiers = assetPlantPhotoCollection.map(assetPlantPhotoItem =>
        this.getAssetPlantPhotoIdentifier(assetPlantPhotoItem),
      );
      const assetPlantPhotosToAdd = assetPlantPhotos.filter(assetPlantPhotoItem => {
        const assetPlantPhotoIdentifier = this.getAssetPlantPhotoIdentifier(assetPlantPhotoItem);
        if (assetPlantPhotoCollectionIdentifiers.includes(assetPlantPhotoIdentifier)) {
          return false;
        }
        assetPlantPhotoCollectionIdentifiers.push(assetPlantPhotoIdentifier);
        return true;
      });
      return [...assetPlantPhotosToAdd, ...assetPlantPhotoCollection];
    }
    return assetPlantPhotoCollection;
  }
}
