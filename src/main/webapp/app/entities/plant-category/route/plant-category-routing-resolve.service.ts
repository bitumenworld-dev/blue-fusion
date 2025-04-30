import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlantCategory } from '../plant-category.model';
import { PlantCategoryService } from '../service/plant-category.service';

const plantCategoryResolve = (route: ActivatedRouteSnapshot): Observable<null | IPlantCategory> => {
  const id = route.params.id;
  if (id) {
    return inject(PlantCategoryService)
      .find(id)
      .pipe(
        mergeMap((plantCategory: HttpResponse<IPlantCategory>) => {
          if (plantCategory.body) {
            return of(plantCategory.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default plantCategoryResolve;
