import { IManufacturerModel, NewManufacturerModel } from './manufacturer-model.model';

export const sampleWithRequiredData: IManufacturerModel = {
  id: 13288,
};

export const sampleWithPartialData: IManufacturerModel = {
  id: 24727,
  modelId: 14123,
};

export const sampleWithFullData: IManufacturerModel = {
  id: 17730,
  modelId: 28887,
  modelName: 'um',
};

export const sampleWithNewData: NewManufacturerModel = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
