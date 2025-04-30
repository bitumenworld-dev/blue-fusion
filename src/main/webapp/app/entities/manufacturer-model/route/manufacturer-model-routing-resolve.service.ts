import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IManufacturerModel } from '../manufacturer-model.model';
import { ManufacturerModelService } from '../service/manufacturer-model.service';

const manufacturerModelResolve = (route: ActivatedRouteSnapshot): Observable<null | IManufacturerModel> => {
  const id = route.params.id;
  if (id) {
    return inject(ManufacturerModelService)
      .find(id)
      .pipe(
        mergeMap((manufacturerModel: HttpResponse<IManufacturerModel>) => {
          if (manufacturerModel.body) {
            return of(manufacturerModel.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default manufacturerModelResolve;
