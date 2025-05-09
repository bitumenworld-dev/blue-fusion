import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFuelIssuanceType } from '../fuel-issuance-type.model';
import { FuelIssuanceTypeService } from '../service/fuel-issuance-type.service';

const fuelIssuanceTypeResolve = (route: ActivatedRouteSnapshot): Observable<null | IFuelIssuanceType> => {
  const id = route.params.id;
  if (id) {
    return inject(FuelIssuanceTypeService)
      .find(id)
      .pipe(
        mergeMap((fuelIssuanceType: HttpResponse<IFuelIssuanceType>) => {
          if (fuelIssuanceType.body) {
            return of(fuelIssuanceType.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default fuelIssuanceTypeResolve;
