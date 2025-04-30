import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import AssetPlantServiceReadingResolve from './route/asset-plant-service-reading-routing-resolve.service';

const assetPlantServiceReadingRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/asset-plant-service-reading.component').then(m => m.AssetPlantServiceReadingComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () =>
      import('./detail/asset-plant-service-reading-detail.component').then(m => m.AssetPlantServiceReadingDetailComponent),
    resolve: {
      assetPlantServiceReading: AssetPlantServiceReadingResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () =>
      import('./update/asset-plant-service-reading-update.component').then(m => m.AssetPlantServiceReadingUpdateComponent),
    resolve: {
      assetPlantServiceReading: AssetPlantServiceReadingResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () =>
      import('./update/asset-plant-service-reading-update.component').then(m => m.AssetPlantServiceReadingUpdateComponent),
    resolve: {
      assetPlantServiceReading: AssetPlantServiceReadingResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default assetPlantServiceReadingRoute;
