import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFuelTransactionHeader } from '../fuel-transaction-header.model';
import { FuelTransactionHeaderService } from '../service/fuel-transaction-header.service';

const fuelTransactionHeaderResolve = (route: ActivatedRouteSnapshot): Observable<null | IFuelTransactionHeader> => {
  const id = route.params.id;
  if (id) {
    return inject(FuelTransactionHeaderService)
      .find(id)
      .pipe(
        mergeMap((fuelTransactionHeader: HttpResponse<IFuelTransactionHeader>) => {
          if (fuelTransactionHeader.body) {
            return of(fuelTransactionHeader.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default fuelTransactionHeaderResolve;
