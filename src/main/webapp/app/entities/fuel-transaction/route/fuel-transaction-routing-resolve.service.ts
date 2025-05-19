import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { FuelTransaction } from '../fuel-transaction.model';
import { FuelTransactionService } from '../service/fuel-transaction.service';

const fuelTransactionResolve = (route: ActivatedRouteSnapshot): Observable<null | FuelTransaction> => {
  const id = route.params.id;
  if (id) {
    return inject(FuelTransactionService)
      .find(id)
      .pipe(
        mergeMap((fuelTransaction: HttpResponse<FuelTransaction>) => {
          if (fuelTransaction.body) {
            return of(fuelTransaction.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default fuelTransactionResolve;
