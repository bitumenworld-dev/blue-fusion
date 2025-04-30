import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPlantSubcategory, NewPlantSubcategory } from '../plant-subcategory.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlantSubcategory for edit and NewPlantSubcategoryFormGroupInput for create.
 */
type PlantSubcategoryFormGroupInput = IPlantSubcategory | PartialWithRequiredKeyOf<NewPlantSubcategory>;

type PlantSubcategoryFormDefaults = Pick<NewPlantSubcategory, 'id' | 'isAudited'>;

type PlantSubcategoryFormGroupContent = {
  id: FormControl<IPlantSubcategory['id'] | NewPlantSubcategory['id']>;
  plantSubcategoryId: FormControl<IPlantSubcategory['plantSubcategoryId']>;
  plantSubcategoryCode: FormControl<IPlantSubcategory['plantSubcategoryCode']>;
  plantSubcategoryName: FormControl<IPlantSubcategory['plantSubcategoryName']>;
  plantSubcategoryDescription: FormControl<IPlantSubcategory['plantSubcategoryDescription']>;
  isAudited: FormControl<IPlantSubcategory['isAudited']>;
};

export type PlantSubcategoryFormGroup = FormGroup<PlantSubcategoryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PlantSubcategoryFormService {
  createPlantSubcategoryFormGroup(plantSubcategory: PlantSubcategoryFormGroupInput = { id: null }): PlantSubcategoryFormGroup {
    const plantSubcategoryRawValue = {
      ...this.getFormDefaults(),
      ...plantSubcategory,
    };
    return new FormGroup<PlantSubcategoryFormGroupContent>({
      id: new FormControl(
        { value: plantSubcategoryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      plantSubcategoryId: new FormControl(plantSubcategoryRawValue.plantSubcategoryId),
      plantSubcategoryCode: new FormControl(plantSubcategoryRawValue.plantSubcategoryCode),
      plantSubcategoryName: new FormControl(plantSubcategoryRawValue.plantSubcategoryName),
      plantSubcategoryDescription: new FormControl(plantSubcategoryRawValue.plantSubcategoryDescription),
      isAudited: new FormControl(plantSubcategoryRawValue.isAudited),
    });
  }

  getPlantSubcategory(form: PlantSubcategoryFormGroup): IPlantSubcategory | NewPlantSubcategory {
    return form.getRawValue() as IPlantSubcategory | NewPlantSubcategory;
  }

  resetForm(form: PlantSubcategoryFormGroup, plantSubcategory: PlantSubcategoryFormGroupInput): void {
    const plantSubcategoryRawValue = { ...this.getFormDefaults(), ...plantSubcategory };
    form.reset(
      {
        ...plantSubcategoryRawValue,
        id: { value: plantSubcategoryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PlantSubcategoryFormDefaults {
    return {
      id: null,
      isAudited: false,
    };
  }
}
