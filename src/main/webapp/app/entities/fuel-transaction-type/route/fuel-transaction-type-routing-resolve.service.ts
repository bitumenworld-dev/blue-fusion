import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFuelTransactionType } from '../fuel-transaction-type.model';
import { FuelTransactionTypeService } from '../service/fuel-transaction-type.service';

const fuelTransactionTypeResolve = (route: ActivatedRouteSnapshot): Observable<null | IFuelTransactionType> => {
  const id = route.params.id;
  if (id) {
    return inject(FuelTransactionTypeService)
      .find(id)
      .pipe(
        mergeMap((fuelTransactionType: HttpResponse<IFuelTransactionType>) => {
          if (fuelTransactionType.body) {
            return of(fuelTransactionType.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default fuelTransactionTypeResolve;
