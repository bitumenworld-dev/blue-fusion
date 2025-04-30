import { IContractDivision, NewContractDivision } from './contract-division.model';

export const sampleWithRequiredData: IContractDivision = {
  id: 13520,
};

export const sampleWithPartialData: IContractDivision = {
  id: 17598,
  contractDivisionNumber: 'helplessly',
  buildSmartReference: 'bitter whose',
  shiftEnd: '01:26:00',
};

export const sampleWithFullData: IContractDivision = {
  id: 5556,
  contractDivisionId: 28454,
  contractDivisionNumber: 'mozzarella',
  companyId: 8272,
  buildSmartReference: 'during',
  shiftStart: '14:31:00',
  shiftEnd: '13:20:00',
  type: 'DIVISION',
  completed: true,
};

export const sampleWithNewData: NewContractDivision = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
