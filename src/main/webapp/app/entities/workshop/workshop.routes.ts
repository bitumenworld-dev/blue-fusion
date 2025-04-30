import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import WorkshopResolve from './route/workshop-routing-resolve.service';

const workshopRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/workshop.component').then(m => m.WorkshopComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/workshop-detail.component').then(m => m.WorkshopDetailComponent),
    resolve: {
      workshop: WorkshopResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/workshop-update.component').then(m => m.WorkshopUpdateComponent),
    resolve: {
      workshop: WorkshopResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/workshop-update.component').then(m => m.WorkshopUpdateComponent),
    resolve: {
      workshop: WorkshopResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default workshopRoute;
