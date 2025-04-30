import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAssetPlant } from '../asset-plant.model';
import { AssetPlantService } from '../service/asset-plant.service';

const assetPlantResolve = (route: ActivatedRouteSnapshot): Observable<null | IAssetPlant> => {
  const id = route.params.id;
  if (id) {
    return inject(AssetPlantService)
      .find(id)
      .pipe(
        mergeMap((assetPlant: HttpResponse<IAssetPlant>) => {
          if (assetPlant.body) {
            return of(assetPlant.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default assetPlantResolve;
