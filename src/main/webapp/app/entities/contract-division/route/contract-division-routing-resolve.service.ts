import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContractDivision } from '../contract-division.model';
import { ContractDivisionService } from '../service/contract-division.service';

const contractDivisionResolve = (route: ActivatedRouteSnapshot): Observable<null | IContractDivision> => {
  const id = route.params.id;
  if (id) {
    return inject(ContractDivisionService)
      .find(id)
      .pipe(
        mergeMap((contractDivision: HttpResponse<IContractDivision>) => {
          if (contractDivision.body) {
            return of(contractDivision.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default contractDivisionResolve;
