import { IFuelIssueanceType, NewFuelIssueanceType } from './fuel-issuance-type.model';

export const sampleWithRequiredData: IFuelIssueanceType = {
  id: 20019,
};

export const sampleWithPartialData: IFuelIssueanceType = {
  id: 2782,
  fuelIssueType: 'yet',
};

export const sampleWithFullData: IFuelIssueanceType = {
  id: 10081,
  fuelIssueTypeId: 22311,
  fuelIssueType: 'fooey as',
};

export const sampleWithNewData: NewFuelIssueanceType = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
