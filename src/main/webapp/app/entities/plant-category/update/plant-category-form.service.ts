import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPlantCategory, NewPlantCategory } from '../plant-category.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlantCategory for edit and NewPlantCategoryFormGroupInput for create.
 */
type PlantCategoryFormGroupInput = IPlantCategory | PartialWithRequiredKeyOf<NewPlantCategory>;

type PlantCategoryFormDefaults = Pick<NewPlantCategory, 'id' | 'isAudited'>;

type PlantCategoryFormGroupContent = {
  id: FormControl<IPlantCategory['id'] | NewPlantCategory['id']>;
  plantCategoryId: FormControl<IPlantCategory['plantCategoryId']>;
  plantCategoryCode: FormControl<IPlantCategory['plantCategoryCode']>;
  plantCategoryName: FormControl<IPlantCategory['plantCategoryName']>;
  plantCategoryDescription: FormControl<IPlantCategory['plantCategoryDescription']>;
  isAudited: FormControl<IPlantCategory['isAudited']>;
};

export type PlantCategoryFormGroup = FormGroup<PlantCategoryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PlantCategoryFormService {
  createPlantCategoryFormGroup(plantCategory: PlantCategoryFormGroupInput = { id: null }): PlantCategoryFormGroup {
    const plantCategoryRawValue = {
      ...this.getFormDefaults(),
      ...plantCategory,
    };
    return new FormGroup<PlantCategoryFormGroupContent>({
      id: new FormControl(
        { value: plantCategoryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      plantCategoryId: new FormControl(plantCategoryRawValue.plantCategoryId),
      plantCategoryCode: new FormControl(plantCategoryRawValue.plantCategoryCode),
      plantCategoryName: new FormControl(plantCategoryRawValue.plantCategoryName),
      plantCategoryDescription: new FormControl(plantCategoryRawValue.plantCategoryDescription),
      isAudited: new FormControl(plantCategoryRawValue.isAudited),
    });
  }

  getPlantCategory(form: PlantCategoryFormGroup): IPlantCategory | NewPlantCategory {
    return form.getRawValue() as IPlantCategory | NewPlantCategory;
  }

  resetForm(form: PlantCategoryFormGroup, plantCategory: PlantCategoryFormGroupInput): void {
    const plantCategoryRawValue = { ...this.getFormDefaults(), ...plantCategory };
    form.reset(
      {
        ...plantCategoryRawValue,
        id: { value: plantCategoryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PlantCategoryFormDefaults {
    return {
      id: null,
      isAudited: false,
    };
  }
}
