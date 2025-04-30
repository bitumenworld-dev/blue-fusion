import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import FuelTransactionLineResolve from './route/fuel-transaction-line-routing-resolve.service';

const fuelTransactionLineRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/fuel-transaction-line.component').then(m => m.FuelTransactionLineComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/fuel-transaction-line-detail.component').then(m => m.FuelTransactionLineDetailComponent),
    resolve: {
      fuelTransactionLine: FuelTransactionLineResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/fuel-transaction-line-update.component').then(m => m.FuelTransactionLineUpdateComponent),
    resolve: {
      fuelTransactionLine: FuelTransactionLineResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/fuel-transaction-line-update.component').then(m => m.FuelTransactionLineUpdateComponent),
    resolve: {
      fuelTransactionLine: FuelTransactionLineResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default fuelTransactionLineRoute;
