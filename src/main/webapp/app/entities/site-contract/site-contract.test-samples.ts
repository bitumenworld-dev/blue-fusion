import { ISiteContract, NewSiteContract } from './site-contract.model';

export const sampleWithRequiredData: ISiteContract = {
  id: 8314,
};

export const sampleWithPartialData: ISiteContract = {
  id: 17439,
};

export const sampleWithFullData: ISiteContract = {
  id: 271,
  siteContractId: 21709,
  siteId: 10638,
  contractId: 18510,
};

export const sampleWithNewData: NewSiteContract = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
