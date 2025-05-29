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
    path: 'storage',
    data: { pageTitle: 'blueFusionApp.storage.home.title' },
    loadChildren: () => import('./storage/storage.routes'),
  },
  {
    path: 'fuel-issuance-type',
    data: { pageTitle: 'blueFusionApp.fuelIssuanceType.home.title' },
    loadChildren: () => import('./fuel-issuance-type/fuel-issuance-type.routes'),
  },
  {
    path: 'asset-plant-photo',
    data: { pageTitle: 'blueFusionApp.assetPlantPhoto.home.title' },
    loadChildren: () => import('./asset-plant-photo/asset-plant-photo.routes'),
  },
  {
    path: 'contract-division',
    data: { pageTitle: 'blueFusionApp.contractDivision.home.title' },
    loadChildren: () => import('./contract-division/contract-division.routes'),
  },
  {
    path: 'site',
    data: { pageTitle: 'blueFusionApp.site.home.title' },
    loadChildren: () => import('./site/site.routes'),
  },
  {
    path: 'company',
    data: { pageTitle: 'blueFusionApp.company.home.title' },
    loadChildren: () => import('./company/company.routes'),
  },
  {
    path: 'plant-category',
    data: { pageTitle: 'blueFusionApp.plantCategory.home.title' },
    loadChildren: () => import('./plant-category/plant-category.routes'),
  },
  {
    path: 'plant-subcategory',
    data: { pageTitle: 'blueFusionApp.plantSubcategory.home.title' },
    loadChildren: () => import('./plant-subcategory/plant-subcategory.routes'),
  },
  {
    path: 'workshop',
    data: { pageTitle: 'blueFusionApp.workshop.home.title' },
    loadChildren: () => import('./workshop/workshop.routes'),
  },
  {
    path: 'site-contract',
    data: { pageTitle: 'blueFusionApp.siteContract.home.title' },
    loadChildren: () => import('./site-contract/site-contract.routes'),
  },
  {
    path: 'employee',
    data: { pageTitle: 'blueFusionApp.employee.home.title' },
    loadChildren: () => import('./employee/employee.routes'),
  },
  {
    path: 'fuel-transaction-type',
    data: { pageTitle: 'blueFusionApp.fuelTransactionType.home.title' },
    loadChildren: () => import('./fuel-transaction-type/fuel-transaction-type.routes'),
  },
  {
    path: 'fuel-transaction',
    data: { pageTitle: 'Fuel Transaction' },
    loadChildren: () => import('./fuel-transaction/fuel-transaction.routes'),
  },
  {
    path: 'fuel-pump',
    data: { pageTitle: 'blueFusionApp.fuelPump.home.title' },
    loadChildren: () => import('./fuel-pump/fuel-pump.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
