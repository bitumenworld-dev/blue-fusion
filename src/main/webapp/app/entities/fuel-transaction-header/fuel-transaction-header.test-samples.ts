import { IFuelTransactionHeader, NewFuelTransactionHeader } from './fuel-transaction-header.model';

export const sampleWithRequiredData: IFuelTransactionHeader = {
  id: 11450,
};

export const sampleWithPartialData: IFuelTransactionHeader = {
  id: 27634,
  transactionTypeId: 19593,
  orderNumber: 'gadzooks',
  deliveryNote: 'safe bitter aha',
  grvNumber: 'sternly snarling ick',
  invoiceNumber: 'gosh yawn right',
  pricePerLitre: 10309.44,
  note: 'lest',
  registrationNumber: 'as whoever for',
};

export const sampleWithFullData: IFuelTransactionHeader = {
  id: 10726,
  fuelTransactionHeaderId: 16872,
  companyId: 30056,
  supplierId: 25237,
  transactionTypeId: 13878,
  fuelType: 'DIESEL',
  orderNumber: 'opposite navigate thankfully',
  deliveryNote: 'stigmatize well-made',
  grvNumber: 'redound openly cheap',
  invoiceNumber: 'anenst pro',
  pricePerLitre: 30515.77,
  note: 'yak',
  registrationNumber: 'whoa recede',
  attendeeId: 29577,
  operatorId: 21123,
};

export const sampleWithNewData: NewFuelTransactionHeader = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
