import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAssetPlantServiceReading } from '../asset-plant-service-reading.model';
import { AssetPlantServiceReadingService } from '../service/asset-plant-service-reading.service';

const assetPlantServiceReadingResolve = (route: ActivatedRouteSnapshot): Observable<null | IAssetPlantServiceReading> => {
  const id = route.params.id;
  if (id) {
    return inject(AssetPlantServiceReadingService)
      .find(id)
      .pipe(
        mergeMap((assetPlantServiceReading: HttpResponse<IAssetPlantServiceReading>) => {
          if (assetPlantServiceReading.body) {
            return of(assetPlantServiceReading.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default assetPlantServiceReadingResolve;
