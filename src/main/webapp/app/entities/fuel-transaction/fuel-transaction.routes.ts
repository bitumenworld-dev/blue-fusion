import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import FuelTransactionResolve from './route/fuel-transaction-routing-resolve.service';

const fuelTransactionRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/fuel-transaction.component').then(m => m.FuelTransactionComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/fuel-transaction-detail.component').then(m => m.FuelTransactionDetailComponent),
    resolve: {
      fuelTransaction: FuelTransactionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/fuel-transaction-update.component').then(m => m.FuelTransactionUpdateComponent),
    resolve: {
      fuelTransaction: FuelTransactionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/fuel-transaction-update.component').then(m => m.FuelTransactionUpdateComponent),
    resolve: {
      fuelTransaction: FuelTransactionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default fuelTransactionRoute;
