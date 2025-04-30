import { IManufacturer, NewManufacturer } from './manufacturer.model';

export const sampleWithRequiredData: IManufacturer = {
  id: 14753,
};

export const sampleWithPartialData: IManufacturer = {
  id: 11527,
  manufacturerId: 10172,
  manufacturerName: 'whoa',
};

export const sampleWithFullData: IManufacturer = {
  id: 3071,
  manufacturerId: 23467,
  manufacturerName: 'overconfidently mmm gah',
};

export const sampleWithNewData: NewManufacturer = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
