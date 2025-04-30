import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import FuelPumpResolve from './route/fuel-pump-routing-resolve.service';

const fuelPumpRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/fuel-pump.component').then(m => m.FuelPumpComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/fuel-pump-detail.component').then(m => m.FuelPumpDetailComponent),
    resolve: {
      fuelPump: FuelPumpResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/fuel-pump-update.component').then(m => m.FuelPumpUpdateComponent),
    resolve: {
      fuelPump: FuelPumpResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/fuel-pump-update.component').then(m => m.FuelPumpUpdateComponent),
    resolve: {
      fuelPump: FuelPumpResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default fuelPumpRoute;
