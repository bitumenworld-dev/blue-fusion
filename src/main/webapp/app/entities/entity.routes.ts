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
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
