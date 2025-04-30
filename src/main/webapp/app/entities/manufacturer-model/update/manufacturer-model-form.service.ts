import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IManufacturerModel, NewManufacturerModel } from '../manufacturer-model.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IManufacturerModel for edit and NewManufacturerModelFormGroupInput for create.
 */
type ManufacturerModelFormGroupInput = IManufacturerModel | PartialWithRequiredKeyOf<NewManufacturerModel>;

type ManufacturerModelFormDefaults = Pick<NewManufacturerModel, 'id'>;

type ManufacturerModelFormGroupContent = {
  id: FormControl<IManufacturerModel['id'] | NewManufacturerModel['id']>;
  modelId: FormControl<IManufacturerModel['modelId']>;
  modelName: FormControl<IManufacturerModel['modelName']>;
};

export type ManufacturerModelFormGroup = FormGroup<ManufacturerModelFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ManufacturerModelFormService {
  createManufacturerModelFormGroup(manufacturerModel: ManufacturerModelFormGroupInput = { id: null }): ManufacturerModelFormGroup {
    const manufacturerModelRawValue = {
      ...this.getFormDefaults(),
      ...manufacturerModel,
    };
    return new FormGroup<ManufacturerModelFormGroupContent>({
      id: new FormControl(
        { value: manufacturerModelRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      modelId: new FormControl(manufacturerModelRawValue.modelId),
      modelName: new FormControl(manufacturerModelRawValue.modelName),
    });
  }

  getManufacturerModel(form: ManufacturerModelFormGroup): IManufacturerModel | NewManufacturerModel {
    return form.getRawValue() as IManufacturerModel | NewManufacturerModel;
  }

  resetForm(form: ManufacturerModelFormGroup, manufacturerModel: ManufacturerModelFormGroupInput): void {
    const manufacturerModelRawValue = { ...this.getFormDefaults(), ...manufacturerModel };
    form.reset(
      {
        ...manufacturerModelRawValue,
        id: { value: manufacturerModelRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ManufacturerModelFormDefaults {
    return {
      id: null,
    };
  }
}
