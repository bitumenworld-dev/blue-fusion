import { IFuelTransactionType, NewFuelTransactionType } from './fuel-transaction-type.model';

export const sampleWithRequiredData: IFuelTransactionType = {
  id: 19433,
};

export const sampleWithPartialData: IFuelTransactionType = {
  id: 7298,
  isActive: true,
};

export const sampleWithFullData: IFuelTransactionType = {
  id: 18704,
  fuelTransactionTypeId: 11464,
  fuelTransactionType: 'hmph',
  isActive: true,
};

export const sampleWithNewData: NewFuelTransactionType = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
