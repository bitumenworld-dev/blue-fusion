import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import StorageResolve from './route/storage-routing-resolve.service';
import SiteResolve from '../site/route/site-routing-resolve.service';

const storageRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/storage.component').then(m => m.StorageComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/storage-detail.component').then(m => m.StorageDetailComponent),
    resolve: {
      storage: StorageResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/storage-update.component').then(m => m.StorageUpdateComponent),
    resolve: {
      storage: StorageResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/storage-update.component').then(m => m.StorageUpdateComponent),
    resolve: {
      storage: StorageResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default storageRoute;
