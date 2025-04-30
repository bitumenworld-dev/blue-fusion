import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'blueFusionApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'asset-plant',
    data: { pageTitle: 'blueFusionApp.assetPlant.home.title' },
    loadChildren: () => import('./asset-plant/asset-plant.routes'),
  },
  {
    path: 'asset-plant-service-reading',
    data: { pageTitle: 'blueFusionApp.assetPlantServiceReading.home.title' },
    loadChildren: () => import('./asset-plant-service-reading/asset-plant-service-reading.routes'),
  },
  {
    path: 'fuel-issueance-type',
    data: { pageTitle: 'blueFusionApp.fuelIssueanceType.home.title' },
    loadChildren: () => import('./fuel-issueance-type/fuel-issueance-type.routes'),
  },
  {
    path: 'asset-plant-photo',
    data: { pageTitle: 'blueFusionApp.assetPlantPhoto.home.title' },
    loadChildren: () => import('./asset-plant-photo/asset-plant-photo.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
