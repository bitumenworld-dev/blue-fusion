import { IAssetPlant, NewAssetPlant } from './asset-plant.model';

export const sampleWithRequiredData: IAssetPlant = {
  id: 20859,
};

export const sampleWithPartialData: IAssetPlant = {
  id: 7319,
  assetPlantId: 12459,
  fleetNumber: 'now provided',
  numberPlate: 'misjudge',
  fleetDescription: 'gosh upon clinking',
  ownerId: 14636,
  plantCategoryId: 22010,
  manufacturerId: 21353,
  yearOfManufacture: 21589,
  colour: 'kiss retool',
  smrReaderType: 'HOUR',
  currentSmrIndex: 1784,
  currentSiteId: 24830,
  currentOperatorId: 10603,
  fuelType: 'DIESEL',
  tankCapacityLitres: 25506.61,
  consumptionUnit: 'LITRES_PER_HOUR',
  capacityTons: 13663.15,
  capacityM3Tight: 5450.38,
  maximumConsumption: 28661.81,
  minimumConsumption: 5581.06,
  trackSmrReading: true,
  trackService: true,
  isDeleted: false,
  requestWeeklyMileage: false,
  chassisNumber: 'lest',
  currentLocation: 'low',
};

export const sampleWithFullData: IAssetPlant = {
  id: 14693,
  assetPlantId: 19547,
  fleetNumber: 'ack immaculate',
  numberPlate: 'towards puzzled',
  fleetDescription: 'coolly surprised apud',
  ownerId: 15335,
  accessibleByCompany: 'swiftly gadzooks misfire',
  driverOrOperator: 'OPERATOR',
  plantCategoryId: 2051,
  plantSubcategoryId: 'bad',
  manufacturerId: 878,
  modelId: 23287,
  yearOfManufacture: 27791,
  colour: 'triangular which imp',
  horseOrTrailer: 'NONE',
  smrReaderType: 'HOUR',
  currentSmrIndex: 9818,
  engineNumber: 'what madly joshingly',
  engineCapacityCc: 'if',
  currentSiteId: 9011,
  currentContractId: 9934,
  currentOperatorId: 967,
  ledgerCode: 'apostrophize hornet',
  fuelType: 'PETROL',
  tankCapacityLitres: 2378.6,
  consumptionUnit: 'KILOMETERS_PER_LITRE',
  plantHoursStatus: 'OPERATIONAL',
  isPrimeMover: false,
  capacityTons: 13754.03,
  capacityM3Loose: 27633.86,
  capacityM3Tight: 1053.3,
  maximumConsumption: 24503.91,
  minimumConsumption: 21289.44,
  maximumSmrOnFullTank: 6244.3,
  trackConsumption: true,
  trackSmrReading: false,
  trackService: true,
  isDeleted: true,
  requestWeeklyMileage: true,
  sent: true,
  chassisNumber: 'while',
  currentLocation: 'storyboard',
};

export const sampleWithNewData: NewAssetPlant = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
