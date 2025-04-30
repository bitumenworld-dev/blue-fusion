import { IAssetPlantPhoto, NewAssetPlantPhoto } from './asset-plant-photo.model';

export const sampleWithRequiredData: IAssetPlantPhoto = {
  id: 18681,
};

export const sampleWithPartialData: IAssetPlantPhoto = {
  id: 21383,
  assetPlantPhotoId: 23109,
  name: 'gah',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
};

export const sampleWithFullData: IAssetPlantPhoto = {
  id: 8995,
  assetPlantPhotoId: 9336,
  name: 'thorny pleasing',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
  assetPlantId: 6137,
  assetPlantPhotoLabel: 'DATA_PLATE',
};

export const sampleWithNewData: NewAssetPlantPhoto = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
