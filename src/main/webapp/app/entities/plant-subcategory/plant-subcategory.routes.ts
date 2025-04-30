import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import PlantSubcategoryResolve from './route/plant-subcategory-routing-resolve.service';

const plantSubcategoryRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/plant-subcategory.component').then(m => m.PlantSubcategoryComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/plant-subcategory-detail.component').then(m => m.PlantSubcategoryDetailComponent),
    resolve: {
      plantSubcategory: PlantSubcategoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/plant-subcategory-update.component').then(m => m.PlantSubcategoryUpdateComponent),
    resolve: {
      plantSubcategory: PlantSubcategoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/plant-subcategory-update.component').then(m => m.PlantSubcategoryUpdateComponent),
    resolve: {
      plantSubcategory: PlantSubcategoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default plantSubcategoryRoute;
