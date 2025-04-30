import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import AssetPlantResolve from './route/asset-plant-routing-resolve.service';

const assetPlantRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/asset-plant.component').then(m => m.AssetPlantComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/asset-plant-detail.component').then(m => m.AssetPlantDetailComponent),
    resolve: {
      assetPlant: AssetPlantResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/asset-plant-update.component').then(m => m.AssetPlantUpdateComponent),
    resolve: {
      assetPlant: AssetPlantResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/asset-plant-update.component').then(m => m.AssetPlantUpdateComponent),
    resolve: {
      assetPlant: AssetPlantResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default assetPlantRoute;
