import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFuelIssueanceType } from '../fuel-issueance-type.model';
import { FuelIssueanceTypeService } from '../service/fuel-issueance-type.service';

const fuelIssueanceTypeResolve = (route: ActivatedRouteSnapshot): Observable<null | IFuelIssueanceType> => {
  const id = route.params.id;
  if (id) {
    return inject(FuelIssueanceTypeService)
      .find(id)
      .pipe(
        mergeMap((fuelIssueanceType: HttpResponse<IFuelIssueanceType>) => {
          if (fuelIssueanceType.body) {
            return of(fuelIssueanceType.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default fuelIssueanceTypeResolve;
