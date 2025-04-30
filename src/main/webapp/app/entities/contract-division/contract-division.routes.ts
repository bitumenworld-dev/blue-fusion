import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ContractDivisionResolve from './route/contract-division-routing-resolve.service';

const contractDivisionRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/contract-division.component').then(m => m.ContractDivisionComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/contract-division-detail.component').then(m => m.ContractDivisionDetailComponent),
    resolve: {
      contractDivision: ContractDivisionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/contract-division-update.component').then(m => m.ContractDivisionUpdateComponent),
    resolve: {
      contractDivision: ContractDivisionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/contract-division-update.component').then(m => m.ContractDivisionUpdateComponent),
    resolve: {
      contractDivision: ContractDivisionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default contractDivisionRoute;
