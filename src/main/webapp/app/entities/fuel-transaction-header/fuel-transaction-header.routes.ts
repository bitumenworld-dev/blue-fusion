import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import FuelTransactionHeaderResolve from './route/fuel-transaction-header-routing-resolve.service';

const fuelTransactionHeaderRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/fuel-transaction-header.component').then(m => m.FuelTransactionHeaderComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/fuel-transaction-header-detail.component').then(m => m.FuelTransactionHeaderDetailComponent),
    resolve: {
      fuelTransactionHeader: FuelTransactionHeaderResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/fuel-transaction-header-update.component').then(m => m.FuelTransactionHeaderUpdateComponent),
    resolve: {
      fuelTransactionHeader: FuelTransactionHeaderResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/fuel-transaction-header-update.component').then(m => m.FuelTransactionHeaderUpdateComponent),
    resolve: {
      fuelTransactionHeader: FuelTransactionHeaderResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default fuelTransactionHeaderRoute;
