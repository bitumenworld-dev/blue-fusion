import dayjs from 'dayjs/esm';

import { IAssetPlantServiceReading, NewAssetPlantServiceReading } from './asset-plant-service-reading.model';

export const sampleWithRequiredData: IAssetPlantServiceReading = {
  id: 16160,
};

export const sampleWithPartialData: IAssetPlantServiceReading = {
  id: 4752,
  assetPlantId: 9175,
  nextServiceSmrReading: 3629.15,
  estimatedNextServiceDate: dayjs('2025-04-30'),
  latestSmrDate: dayjs('2025-04-30'),
  lastServiceSmr: 12387.95,
  serviceUnit: 'HOUR_BASED',
};

export const sampleWithFullData: IAssetPlantServiceReading = {
  id: 30014,
  assetPlantServiceReadingId: 13807,
  assetPlantId: 22779,
  nextServiceSmrReading: 14475.67,
  estimatedUnitsPerDay: 6894.78,
  estimatedNextServiceDate: dayjs('2025-04-29'),
  latestSmrReadings: 11897.19,
  serviceInterval: 22498.2,
  lastServiceDate: dayjs('2025-04-29'),
  latestSmrDate: dayjs('2025-04-29'),
  lastServiceSmr: 14735.47,
  serviceUnit: 'KILOMETER_BASED',
};

export const sampleWithNewData: NewAssetPlantServiceReading = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
