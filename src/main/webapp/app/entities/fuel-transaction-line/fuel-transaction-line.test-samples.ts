import { IFuelTransactionLine, NewFuelTransactionLine } from './fuel-transaction-line.model';

export const sampleWithRequiredData: IFuelTransactionLine = {
  id: 25732,
};

export const sampleWithPartialData: IFuelTransactionLine = {
  id: 8512,
  fuelTransactionLineId: 13707,
  fuelTransactionHeaderId: 6205,
  issuanceTypeId: 7001,
  pumpId: 1505,
  litres: 27094.56,
  meterReadingStart: 24200.96,
  meterReadingEnd: 25646.8,
};

export const sampleWithFullData: IFuelTransactionLine = {
  id: 1074,
  fuelTransactionLineId: 25848,
  fuelTransactionHeaderId: 25693,
  assetPlantId: 21100,
  contractDivisionId: 22084,
  issuanceTypeId: 6335,
  pumpId: 29355,
  storageUnitId: 30405,
  litres: 3161.4,
  meterReadingStart: 24579.65,
  meterReadingEnd: 31976.23,
  multiplier: 21104,
};

export const sampleWithNewData: NewFuelTransactionLine = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
