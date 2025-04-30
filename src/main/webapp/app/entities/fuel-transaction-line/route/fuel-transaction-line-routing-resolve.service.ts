import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFuelTransactionLine } from '../fuel-transaction-line.model';
import { FuelTransactionLineService } from '../service/fuel-transaction-line.service';

const fuelTransactionLineResolve = (route: ActivatedRouteSnapshot): Observable<null | IFuelTransactionLine> => {
  const id = route.params.id;
  if (id) {
    return inject(FuelTransactionLineService)
      .find(id)
      .pipe(
        mergeMap((fuelTransactionLine: HttpResponse<IFuelTransactionLine>) => {
          if (fuelTransactionLine.body) {
            return of(fuelTransactionLine.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default fuelTransactionLineResolve;
