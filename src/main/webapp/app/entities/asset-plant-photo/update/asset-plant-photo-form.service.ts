import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IAssetPlantPhoto, NewAssetPlantPhoto } from '../asset-plant-photo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAssetPlantPhoto for edit and NewAssetPlantPhotoFormGroupInput for create.
 */
type AssetPlantPhotoFormGroupInput = IAssetPlantPhoto | PartialWithRequiredKeyOf<NewAssetPlantPhoto>;

type AssetPlantPhotoFormDefaults = Pick<NewAssetPlantPhoto, 'id'>;

type AssetPlantPhotoFormGroupContent = {
  id: FormControl<IAssetPlantPhoto['id'] | NewAssetPlantPhoto['id']>;
  assetPlantPhotoId: FormControl<IAssetPlantPhoto['assetPlantPhotoId']>;
  name: FormControl<IAssetPlantPhoto['name']>;
  image: FormControl<IAssetPlantPhoto['image']>;
  imageContentType: FormControl<IAssetPlantPhoto['imageContentType']>;
  assetPlantId: FormControl<IAssetPlantPhoto['assetPlantId']>;
  assetPlantPhotoLabel: FormControl<IAssetPlantPhoto['assetPlantPhotoLabel']>;
};

export type AssetPlantPhotoFormGroup = FormGroup<AssetPlantPhotoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AssetPlantPhotoFormService {
  createAssetPlantPhotoFormGroup(assetPlantPhoto: AssetPlantPhotoFormGroupInput = { id: null }): AssetPlantPhotoFormGroup {
    const assetPlantPhotoRawValue = {
      ...this.getFormDefaults(),
      ...assetPlantPhoto,
    };
    return new FormGroup<AssetPlantPhotoFormGroupContent>({
      id: new FormControl(
        { value: assetPlantPhotoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      assetPlantPhotoId: new FormControl(assetPlantPhotoRawValue.assetPlantPhotoId),
      name: new FormControl(assetPlantPhotoRawValue.name),
      image: new FormControl(assetPlantPhotoRawValue.image),
      imageContentType: new FormControl(assetPlantPhotoRawValue.imageContentType),
      assetPlantId: new FormControl(assetPlantPhotoRawValue.assetPlantId),
      assetPlantPhotoLabel: new FormControl(assetPlantPhotoRawValue.assetPlantPhotoLabel),
    });
  }

  getAssetPlantPhoto(form: AssetPlantPhotoFormGroup): IAssetPlantPhoto | NewAssetPlantPhoto {
    return form.getRawValue() as IAssetPlantPhoto | NewAssetPlantPhoto;
  }

  resetForm(form: AssetPlantPhotoFormGroup, assetPlantPhoto: AssetPlantPhotoFormGroupInput): void {
    const assetPlantPhotoRawValue = { ...this.getFormDefaults(), ...assetPlantPhoto };
    form.reset(
      {
        ...assetPlantPhotoRawValue,
        id: { value: assetPlantPhotoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AssetPlantPhotoFormDefaults {
    return {
      id: null,
    };
  }
}
