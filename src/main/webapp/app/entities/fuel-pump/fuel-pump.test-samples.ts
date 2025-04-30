import { IFuelPump, NewFuelPump } from './fuel-pump.model';

export const sampleWithRequiredData: IFuelPump = {
  id: 30341,
};

export const sampleWithPartialData: IFuelPump = {
  id: 22768,
  fuelPumpId: 12326,
  fuelPumpNumber: 'upright tightly',
  currentStorageUnitId: 20513,
};

export const sampleWithFullData: IFuelPump = {
  id: 29271,
  fuelPumpId: 18853,
  fuelPumpNumber: 'phew under',
  currentStorageUnitId: 17829,
};

export const sampleWithNewData: NewFuelPump = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
