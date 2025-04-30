import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import SiteContractResolve from './route/site-contract-routing-resolve.service';

const siteContractRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/site-contract.component').then(m => m.SiteContractComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/site-contract-detail.component').then(m => m.SiteContractDetailComponent),
    resolve: {
      siteContract: SiteContractResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/site-contract-update.component').then(m => m.SiteContractUpdateComponent),
    resolve: {
      siteContract: SiteContractResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/site-contract-update.component').then(m => m.SiteContractUpdateComponent),
    resolve: {
      siteContract: SiteContractResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default siteContractRoute;
