import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Storage } from '../storage.model';
import { StorageService } from '../service/storage.service';

const storageResolve = (route: ActivatedRouteSnapshot): Observable<null | Storage> => {
  const id = route.params.id;
  if (id) {
    return inject(StorageService)
      .find(id)
      .pipe(
        mergeMap((storage: HttpResponse<Storage>) => {
          if (storage.body) {
            return of(storage.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default storageResolve;
