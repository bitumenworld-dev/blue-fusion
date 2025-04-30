import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlantCategory, NewPlantCategory } from '../plant-category.model';

export type PartialUpdatePlantCategory = Partial<IPlantCategory> & Pick<IPlantCategory, 'id'>;

export type EntityResponseType = HttpResponse<IPlantCategory>;
export type EntityArrayResponseType = HttpResponse<IPlantCategory[]>;

@Injectable({ providedIn: 'root' })
export class PlantCategoryService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/plant-categories');

  create(plantCategory: NewPlantCategory): Observable<EntityResponseType> {
    return this.http.post<IPlantCategory>(this.resourceUrl, plantCategory, { observe: 'response' });
  }

  update(plantCategory: IPlantCategory): Observable<EntityResponseType> {
    return this.http.put<IPlantCategory>(`${this.resourceUrl}/${this.getPlantCategoryIdentifier(plantCategory)}`, plantCategory, {
      observe: 'response',
    });
  }

  partialUpdate(plantCategory: PartialUpdatePlantCategory): Observable<EntityResponseType> {
    return this.http.patch<IPlantCategory>(`${this.resourceUrl}/${this.getPlantCategoryIdentifier(plantCategory)}`, plantCategory, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlantCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlantCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPlantCategoryIdentifier(plantCategory: Pick<IPlantCategory, 'id'>): number {
    return plantCategory.id;
  }

  comparePlantCategory(o1: Pick<IPlantCategory, 'id'> | null, o2: Pick<IPlantCategory, 'id'> | null): boolean {
    return o1 && o2 ? this.getPlantCategoryIdentifier(o1) === this.getPlantCategoryIdentifier(o2) : o1 === o2;
  }

  addPlantCategoryToCollectionIfMissing<Type extends Pick<IPlantCategory, 'id'>>(
    plantCategoryCollection: Type[],
    ...plantCategoriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const plantCategories: Type[] = plantCategoriesToCheck.filter(isPresent);
    if (plantCategories.length > 0) {
      const plantCategoryCollectionIdentifiers = plantCategoryCollection.map(plantCategoryItem =>
        this.getPlantCategoryIdentifier(plantCategoryItem),
      );
      const plantCategoriesToAdd = plantCategories.filter(plantCategoryItem => {
        const plantCategoryIdentifier = this.getPlantCategoryIdentifier(plantCategoryItem);
        if (plantCategoryCollectionIdentifiers.includes(plantCategoryIdentifier)) {
          return false;
        }
        plantCategoryCollectionIdentifiers.push(plantCategoryIdentifier);
        return true;
      });
      return [...plantCategoriesToAdd, ...plantCategoryCollection];
    }
    return plantCategoryCollection;
  }
}
