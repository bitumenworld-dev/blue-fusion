import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import FuelIssueanceTypeResolve from './route/fuel-issueance-type-routing-resolve.service';

const fuelIssueanceTypeRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/fuel-issueance-type.component').then(m => m.FuelIssueanceTypeComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/fuel-issueance-type-detail.component').then(m => m.FuelIssueanceTypeDetailComponent),
    resolve: {
      fuelIssueanceType: FuelIssueanceTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/fuel-issueance-type-update.component').then(m => m.FuelIssueanceTypeUpdateComponent),
    resolve: {
      fuelIssueanceType: FuelIssueanceTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/fuel-issueance-type-update.component').then(m => m.FuelIssueanceTypeUpdateComponent),
    resolve: {
      fuelIssueanceType: FuelIssueanceTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default fuelIssueanceTypeRoute;
