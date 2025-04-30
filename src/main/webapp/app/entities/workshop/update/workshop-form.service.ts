import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IWorkshop, NewWorkshop } from '../workshop.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IWorkshop for edit and NewWorkshopFormGroupInput for create.
 */
type WorkshopFormGroupInput = IWorkshop | PartialWithRequiredKeyOf<NewWorkshop>;

type WorkshopFormDefaults = Pick<NewWorkshop, 'id'>;

type WorkshopFormGroupContent = {
  id: FormControl<IWorkshop['id'] | NewWorkshop['id']>;
  workshopId: FormControl<IWorkshop['workshopId']>;
  siteId: FormControl<IWorkshop['siteId']>;
  workshopName: FormControl<IWorkshop['workshopName']>;
};

export type WorkshopFormGroup = FormGroup<WorkshopFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class WorkshopFormService {
  createWorkshopFormGroup(workshop: WorkshopFormGroupInput = { id: null }): WorkshopFormGroup {
    const workshopRawValue = {
      ...this.getFormDefaults(),
      ...workshop,
    };
    return new FormGroup<WorkshopFormGroupContent>({
      id: new FormControl(
        { value: workshopRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      workshopId: new FormControl(workshopRawValue.workshopId),
      siteId: new FormControl(workshopRawValue.siteId),
      workshopName: new FormControl(workshopRawValue.workshopName),
    });
  }

  getWorkshop(form: WorkshopFormGroup): IWorkshop | NewWorkshop {
    return form.getRawValue() as IWorkshop | NewWorkshop;
  }

  resetForm(form: WorkshopFormGroup, workshop: WorkshopFormGroupInput): void {
    const workshopRawValue = { ...this.getFormDefaults(), ...workshop };
    form.reset(
      {
        ...workshopRawValue,
        id: { value: workshopRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): WorkshopFormDefaults {
    return {
      id: null,
    };
  }
}
