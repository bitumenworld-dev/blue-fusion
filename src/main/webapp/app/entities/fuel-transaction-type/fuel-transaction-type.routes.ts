import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import FuelTransactionTypeResolve from './route/fuel-transaction-type-routing-resolve.service';

const fuelTransactionTypeRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/fuel-transaction-type.component').then(m => m.FuelTransactionTypeComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/fuel-transaction-type-detail.component').then(m => m.FuelTransactionTypeDetailComponent),
    resolve: {
      fuelTransactionType: FuelTransactionTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/fuel-transaction-type-update.component').then(m => m.FuelTransactionTypeUpdateComponent),
    resolve: {
      fuelTransactionType: FuelTransactionTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/fuel-transaction-type-update.component').then(m => m.FuelTransactionTypeUpdateComponent),
    resolve: {
      fuelTransactionType: FuelTransactionTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default fuelTransactionTypeRoute;
