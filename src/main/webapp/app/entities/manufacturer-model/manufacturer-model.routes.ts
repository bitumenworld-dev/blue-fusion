import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ManufacturerModelResolve from './route/manufacturer-model-routing-resolve.service';

const manufacturerModelRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/manufacturer-model.component').then(m => m.ManufacturerModelComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/manufacturer-model-detail.component').then(m => m.ManufacturerModelDetailComponent),
    resolve: {
      manufacturerModel: ManufacturerModelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/manufacturer-model-update.component').then(m => m.ManufacturerModelUpdateComponent),
    resolve: {
      manufacturerModel: ManufacturerModelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/manufacturer-model-update.component').then(m => m.ManufacturerModelUpdateComponent),
    resolve: {
      manufacturerModel: ManufacturerModelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default manufacturerModelRoute;
