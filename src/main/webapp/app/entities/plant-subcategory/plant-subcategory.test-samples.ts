import { IPlantSubcategory, NewPlantSubcategory } from './plant-subcategory.model';

export const sampleWithRequiredData: IPlantSubcategory = {
  id: 19119,
};

export const sampleWithPartialData: IPlantSubcategory = {
  id: 2656,
  plantSubcategoryId: 9589,
  plantSubcategoryDescription: 'oh happily',
  isAudited: true,
};

export const sampleWithFullData: IPlantSubcategory = {
  id: 8941,
  plantSubcategoryId: 516,
  plantSubcategoryCode: 'hm',
  plantSubcategoryName: 'airbus meh',
  plantSubcategoryDescription: 'staid beautifully',
  isAudited: false,
};

export const sampleWithNewData: NewPlantSubcategory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
