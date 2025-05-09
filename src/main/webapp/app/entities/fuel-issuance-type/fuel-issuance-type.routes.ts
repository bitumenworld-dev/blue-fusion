import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import FuelIssuanceTypeResolve from './route/fuel-issuance-type-routing-resolve.service';

const fuelIssuanceTypeRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/fuel-issuance-type.component').then(m => m.FuelIssuanceTypeComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/fuel-issuance-type-detail.component').then(m => m.FuelIssuanceTypeDetailComponent),
    resolve: {
      fuelIssuanceType: FuelIssuanceTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/fuel-issuance-type-update.component').then(m => m.FuelIssuanceTypeUpdateComponent),
    resolve: {
      fuelIssuanceType: FuelIssuanceTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/fuel-issuance-type-update.component').then(m => m.FuelIssuanceTypeUpdateComponent),
    resolve: {
      fuelIssuanceType: FuelIssuanceTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default fuelIssuanceTypeRoute;
