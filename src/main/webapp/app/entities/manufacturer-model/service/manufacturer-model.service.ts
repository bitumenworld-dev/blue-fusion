import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IManufacturerModel, NewManufacturerModel } from '../manufacturer-model.model';

export type PartialUpdateManufacturerModel = Partial<IManufacturerModel> & Pick<IManufacturerModel, 'id'>;

export type EntityResponseType = HttpResponse<IManufacturerModel>;
export type EntityArrayResponseType = HttpResponse<IManufacturerModel[]>;

@Injectable({ providedIn: 'root' })
export class ManufacturerModelService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/manufacturer-models');

  create(manufacturerModel: NewManufacturerModel): Observable<EntityResponseType> {
    return this.http.post<IManufacturerModel>(this.resourceUrl, manufacturerModel, { observe: 'response' });
  }

  update(manufacturerModel: IManufacturerModel): Observable<EntityResponseType> {
    return this.http.put<IManufacturerModel>(
      `${this.resourceUrl}/${this.getManufacturerModelIdentifier(manufacturerModel)}`,
      manufacturerModel,
      { observe: 'response' },
    );
  }

  partialUpdate(manufacturerModel: PartialUpdateManufacturerModel): Observable<EntityResponseType> {
    return this.http.patch<IManufacturerModel>(
      `${this.resourceUrl}/${this.getManufacturerModelIdentifier(manufacturerModel)}`,
      manufacturerModel,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IManufacturerModel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IManufacturerModel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getManufacturerModelIdentifier(manufacturerModel: Pick<IManufacturerModel, 'id'>): number {
    return manufacturerModel.id;
  }

  compareManufacturerModel(o1: Pick<IManufacturerModel, 'id'> | null, o2: Pick<IManufacturerModel, 'id'> | null): boolean {
    return o1 && o2 ? this.getManufacturerModelIdentifier(o1) === this.getManufacturerModelIdentifier(o2) : o1 === o2;
  }

  addManufacturerModelToCollectionIfMissing<Type extends Pick<IManufacturerModel, 'id'>>(
    manufacturerModelCollection: Type[],
    ...manufacturerModelsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const manufacturerModels: Type[] = manufacturerModelsToCheck.filter(isPresent);
    if (manufacturerModels.length > 0) {
      const manufacturerModelCollectionIdentifiers = manufacturerModelCollection.map(manufacturerModelItem =>
        this.getManufacturerModelIdentifier(manufacturerModelItem),
      );
      const manufacturerModelsToAdd = manufacturerModels.filter(manufacturerModelItem => {
        const manufacturerModelIdentifier = this.getManufacturerModelIdentifier(manufacturerModelItem);
        if (manufacturerModelCollectionIdentifiers.includes(manufacturerModelIdentifier)) {
          return false;
        }
        manufacturerModelCollectionIdentifiers.push(manufacturerModelIdentifier);
        return true;
      });
      return [...manufacturerModelsToAdd, ...manufacturerModelCollection];
    }
    return manufacturerModelCollection;
  }
}
