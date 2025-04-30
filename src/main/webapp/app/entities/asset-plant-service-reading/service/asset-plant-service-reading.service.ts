import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAssetPlantServiceReading, NewAssetPlantServiceReading } from '../asset-plant-service-reading.model';

export type PartialUpdateAssetPlantServiceReading = Partial<IAssetPlantServiceReading> & Pick<IAssetPlantServiceReading, 'id'>;

type RestOf<T extends IAssetPlantServiceReading | NewAssetPlantServiceReading> = Omit<
  T,
  'estimatedNextServiceDate' | 'lastServiceDate' | 'latestSmrDate'
> & {
  estimatedNextServiceDate?: string | null;
  lastServiceDate?: string | null;
  latestSmrDate?: string | null;
};

export type RestAssetPlantServiceReading = RestOf<IAssetPlantServiceReading>;

export type NewRestAssetPlantServiceReading = RestOf<NewAssetPlantServiceReading>;

export type PartialUpdateRestAssetPlantServiceReading = RestOf<PartialUpdateAssetPlantServiceReading>;

export type EntityResponseType = HttpResponse<IAssetPlantServiceReading>;
export type EntityArrayResponseType = HttpResponse<IAssetPlantServiceReading[]>;

@Injectable({ providedIn: 'root' })
export class AssetPlantServiceReadingService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/asset-plant-service-readings');

  create(assetPlantServiceReading: NewAssetPlantServiceReading): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assetPlantServiceReading);
    return this.http
      .post<RestAssetPlantServiceReading>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(assetPlantServiceReading: IAssetPlantServiceReading): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assetPlantServiceReading);
    return this.http
      .put<RestAssetPlantServiceReading>(
        `${this.resourceUrl}/${this.getAssetPlantServiceReadingIdentifier(assetPlantServiceReading)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(assetPlantServiceReading: PartialUpdateAssetPlantServiceReading): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assetPlantServiceReading);
    return this.http
      .patch<RestAssetPlantServiceReading>(
        `${this.resourceUrl}/${this.getAssetPlantServiceReadingIdentifier(assetPlantServiceReading)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAssetPlantServiceReading>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAssetPlantServiceReading[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAssetPlantServiceReadingIdentifier(assetPlantServiceReading: Pick<IAssetPlantServiceReading, 'id'>): number {
    return assetPlantServiceReading.id;
  }

  compareAssetPlantServiceReading(
    o1: Pick<IAssetPlantServiceReading, 'id'> | null,
    o2: Pick<IAssetPlantServiceReading, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getAssetPlantServiceReadingIdentifier(o1) === this.getAssetPlantServiceReadingIdentifier(o2) : o1 === o2;
  }

  addAssetPlantServiceReadingToCollectionIfMissing<Type extends Pick<IAssetPlantServiceReading, 'id'>>(
    assetPlantServiceReadingCollection: Type[],
    ...assetPlantServiceReadingsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const assetPlantServiceReadings: Type[] = assetPlantServiceReadingsToCheck.filter(isPresent);
    if (assetPlantServiceReadings.length > 0) {
      const assetPlantServiceReadingCollectionIdentifiers = assetPlantServiceReadingCollection.map(assetPlantServiceReadingItem =>
        this.getAssetPlantServiceReadingIdentifier(assetPlantServiceReadingItem),
      );
      const assetPlantServiceReadingsToAdd = assetPlantServiceReadings.filter(assetPlantServiceReadingItem => {
        const assetPlantServiceReadingIdentifier = this.getAssetPlantServiceReadingIdentifier(assetPlantServiceReadingItem);
        if (assetPlantServiceReadingCollectionIdentifiers.includes(assetPlantServiceReadingIdentifier)) {
          return false;
        }
        assetPlantServiceReadingCollectionIdentifiers.push(assetPlantServiceReadingIdentifier);
        return true;
      });
      return [...assetPlantServiceReadingsToAdd, ...assetPlantServiceReadingCollection];
    }
    return assetPlantServiceReadingCollection;
  }

  protected convertDateFromClient<
    T extends IAssetPlantServiceReading | NewAssetPlantServiceReading | PartialUpdateAssetPlantServiceReading,
  >(assetPlantServiceReading: T): RestOf<T> {
    return {
      ...assetPlantServiceReading,
      estimatedNextServiceDate: assetPlantServiceReading.estimatedNextServiceDate?.format(DATE_FORMAT) ?? null,
      lastServiceDate: assetPlantServiceReading.lastServiceDate?.format(DATE_FORMAT) ?? null,
      latestSmrDate: assetPlantServiceReading.latestSmrDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restAssetPlantServiceReading: RestAssetPlantServiceReading): IAssetPlantServiceReading {
    return {
      ...restAssetPlantServiceReading,
      estimatedNextServiceDate: restAssetPlantServiceReading.estimatedNextServiceDate
        ? dayjs(restAssetPlantServiceReading.estimatedNextServiceDate)
        : undefined,
      lastServiceDate: restAssetPlantServiceReading.lastServiceDate ? dayjs(restAssetPlantServiceReading.lastServiceDate) : undefined,
      latestSmrDate: restAssetPlantServiceReading.latestSmrDate ? dayjs(restAssetPlantServiceReading.latestSmrDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAssetPlantServiceReading>): HttpResponse<IAssetPlantServiceReading> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAssetPlantServiceReading[]>): HttpResponse<IAssetPlantServiceReading[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
