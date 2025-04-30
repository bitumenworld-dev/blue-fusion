import { IEmployee, NewEmployee } from './employee.model';

export const sampleWithRequiredData: IEmployee = {
  id: 8899,
};

export const sampleWithPartialData: IEmployee = {
  id: 4463,
  employeeId: 8249,
  contractActive: false,
};

export const sampleWithFullData: IEmployee = {
  id: 19019,
  employeeId: 25574,
  contractActive: false,
};

export const sampleWithNewData: NewEmployee = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
