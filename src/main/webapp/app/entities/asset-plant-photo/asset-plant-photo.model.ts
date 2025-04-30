import { AssetPlantPhotoLabel } from 'app/entities/enumerations/asset-plant-photo-label.model';

export interface IAssetPlantPhoto {
  id: number;
  assetPlantPhotoId?: number | null;
  name?: string | null;
  image?: string | null;
  imageContentType?: string | null;
  assetPlantId?: number | null;
  assetPlantPhotoLabel?: keyof typeof AssetPlantPhotoLabel | null;
}

export type NewAssetPlantPhoto = Omit<IAssetPlantPhoto, 'id'> & { id: null };
