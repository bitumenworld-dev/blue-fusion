import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlantSubcategory } from '../plant-subcategory.model';
import { PlantSubcategoryService } from '../service/plant-subcategory.service';

const plantSubcategoryResolve = (route: ActivatedRouteSnapshot): Observable<null | IPlantSubcategory> => {
  const id = route.params.id;
  if (id) {
    return inject(PlantSubcategoryService)
      .find(id)
      .pipe(
        mergeMap((plantSubcategory: HttpResponse<IPlantSubcategory>) => {
          if (plantSubcategory.body) {
            return of(plantSubcategory.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default plantSubcategoryResolve;
