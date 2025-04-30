import { IWorkshop, NewWorkshop } from './workshop.model';

export const sampleWithRequiredData: IWorkshop = {
  id: 898,
};

export const sampleWithPartialData: IWorkshop = {
  id: 26649,
  workshopId: 3060,
};

export const sampleWithFullData: IWorkshop = {
  id: 23440,
  workshopId: 17096,
  siteId: 28929,
  workshopName: 'phew furthermore against',
};

export const sampleWithNewData: NewWorkshop = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
