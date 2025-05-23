import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { FuelPump } from '../fuel-pump.model';
import { FuelPumpService } from '../service/fuel-pump.service';

const fuelPumpResolve = (route: ActivatedRouteSnapshot): Observable<null | FuelPump> => {
  const id = route.params.id;
  if (id) {
    return inject(FuelPumpService)
      .find(id)
      .pipe(
        mergeMap((fuelPump: HttpResponse<FuelPump>) => {
          if (fuelPump.body) {
            return of(fuelPump.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default fuelPumpResolve;
