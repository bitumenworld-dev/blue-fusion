import { IPlantCategory, NewPlantCategory } from './plant-category.model';

export const sampleWithRequiredData: IPlantCategory = {
  id: 12259,
};

export const sampleWithPartialData: IPlantCategory = {
  id: 15062,
  plantCategoryDescription: 'excess',
  isAudited: false,
};

export const sampleWithFullData: IPlantCategory = {
  id: 10383,
  plantCategoryId: 5184,
  plantCategoryCode: 'dimly quaintly',
  plantCategoryName: 'lest at pastel',
  plantCategoryDescription: 'compromise',
  isAudited: false,
};

export const sampleWithNewData: NewPlantCategory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
