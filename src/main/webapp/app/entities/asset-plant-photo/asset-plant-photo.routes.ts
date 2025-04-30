import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import AssetPlantPhotoResolve from './route/asset-plant-photo-routing-resolve.service';

const assetPlantPhotoRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/asset-plant-photo.component').then(m => m.AssetPlantPhotoComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/asset-plant-photo-detail.component').then(m => m.AssetPlantPhotoDetailComponent),
    resolve: {
      assetPlantPhoto: AssetPlantPhotoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/asset-plant-photo-update.component').then(m => m.AssetPlantPhotoUpdateComponent),
    resolve: {
      assetPlantPhoto: AssetPlantPhotoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/asset-plant-photo-update.component').then(m => m.AssetPlantPhotoUpdateComponent),
    resolve: {
      assetPlantPhoto: AssetPlantPhotoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default assetPlantPhotoRoute;
