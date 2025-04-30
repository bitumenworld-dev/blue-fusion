import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlantSubcategory, NewPlantSubcategory } from '../plant-subcategory.model';

export type PartialUpdatePlantSubcategory = Partial<IPlantSubcategory> & Pick<IPlantSubcategory, 'id'>;

export type EntityResponseType = HttpResponse<IPlantSubcategory>;
export type EntityArrayResponseType = HttpResponse<IPlantSubcategory[]>;

@Injectable({ providedIn: 'root' })
export class PlantSubcategoryService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/plant-subcategories');

  create(plantSubcategory: NewPlantSubcategory): Observable<EntityResponseType> {
    return this.http.post<IPlantSubcategory>(this.resourceUrl, plantSubcategory, { observe: 'response' });
  }

  update(plantSubcategory: IPlantSubcategory): Observable<EntityResponseType> {
    return this.http.put<IPlantSubcategory>(
      `${this.resourceUrl}/${this.getPlantSubcategoryIdentifier(plantSubcategory)}`,
      plantSubcategory,
      { observe: 'response' },
    );
  }

  partialUpdate(plantSubcategory: PartialUpdatePlantSubcategory): Observable<EntityResponseType> {
    return this.http.patch<IPlantSubcategory>(
      `${this.resourceUrl}/${this.getPlantSubcategoryIdentifier(plantSubcategory)}`,
      plantSubcategory,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlantSubcategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlantSubcategory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPlantSubcategoryIdentifier(plantSubcategory: Pick<IPlantSubcategory, 'id'>): number {
    return plantSubcategory.id;
  }

  comparePlantSubcategory(o1: Pick<IPlantSubcategory, 'id'> | null, o2: Pick<IPlantSubcategory, 'id'> | null): boolean {
    return o1 && o2 ? this.getPlantSubcategoryIdentifier(o1) === this.getPlantSubcategoryIdentifier(o2) : o1 === o2;
  }

  addPlantSubcategoryToCollectionIfMissing<Type extends Pick<IPlantSubcategory, 'id'>>(
    plantSubcategoryCollection: Type[],
    ...plantSubcategoriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const plantSubcategories: Type[] = plantSubcategoriesToCheck.filter(isPresent);
    if (plantSubcategories.length > 0) {
      const plantSubcategoryCollectionIdentifiers = plantSubcategoryCollection.map(plantSubcategoryItem =>
        this.getPlantSubcategoryIdentifier(plantSubcategoryItem),
      );
      const plantSubcategoriesToAdd = plantSubcategories.filter(plantSubcategoryItem => {
        const plantSubcategoryIdentifier = this.getPlantSubcategoryIdentifier(plantSubcategoryItem);
        if (plantSubcategoryCollectionIdentifiers.includes(plantSubcategoryIdentifier)) {
          return false;
        }
        plantSubcategoryCollectionIdentifiers.push(plantSubcategoryIdentifier);
        return true;
      });
      return [...plantSubcategoriesToAdd, ...plantSubcategoryCollection];
    }
    return plantSubcategoryCollection;
  }
}
