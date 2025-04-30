import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISiteContract } from '../site-contract.model';
import { SiteContractService } from '../service/site-contract.service';

const siteContractResolve = (route: ActivatedRouteSnapshot): Observable<null | ISiteContract> => {
  const id = route.params.id;
  if (id) {
    return inject(SiteContractService)
      .find(id)
      .pipe(
        mergeMap((siteContract: HttpResponse<ISiteContract>) => {
          if (siteContract.body) {
            return of(siteContract.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default siteContractResolve;
