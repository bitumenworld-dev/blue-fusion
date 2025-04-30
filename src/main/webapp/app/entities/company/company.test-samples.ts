import { ICompany, NewCompany } from './company.model';

export const sampleWithRequiredData: ICompany = {
  id: 6800,
};

export const sampleWithPartialData: ICompany = {
  id: 25647,
  companyId: 8903,
  description: 'within ha but',
};

export const sampleWithFullData: ICompany = {
  id: 14111,
  companyId: 1205,
  name: 'apud considering outrank',
  description: 'joy',
};

export const sampleWithNewData: NewCompany = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
