import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import PlantCategoryResolve from './route/plant-category-routing-resolve.service';

const plantCategoryRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/plant-category.component').then(m => m.PlantCategoryComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/plant-category-detail.component').then(m => m.PlantCategoryDetailComponent),
    resolve: {
      plantCategory: PlantCategoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/plant-category-update.component').then(m => m.PlantCategoryUpdateComponent),
    resolve: {
      plantCategory: PlantCategoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/plant-category-update.component').then(m => m.PlantCategoryUpdateComponent),
    resolve: {
      plantCategory: PlantCategoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default plantCategoryRoute;
