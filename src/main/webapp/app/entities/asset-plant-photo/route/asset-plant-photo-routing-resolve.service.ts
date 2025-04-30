import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAssetPlantPhoto } from '../asset-plant-photo.model';
import { AssetPlantPhotoService } from '../service/asset-plant-photo.service';

const assetPlantPhotoResolve = (route: ActivatedRouteSnapshot): Observable<null | IAssetPlantPhoto> => {
  const id = route.params.id;
  if (id) {
    return inject(AssetPlantPhotoService)
      .find(id)
      .pipe(
        mergeMap((assetPlantPhoto: HttpResponse<IAssetPlantPhoto>) => {
          if (assetPlantPhoto.body) {
            return of(assetPlantPhoto.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default assetPlantPhotoResolve;
