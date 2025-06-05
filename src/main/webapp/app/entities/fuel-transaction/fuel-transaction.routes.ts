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
];

export default fuelTransactionRoute;
