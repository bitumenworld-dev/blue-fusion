import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IContractDivision, NewContractDivision } from '../contract-division.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContractDivision for edit and NewContractDivisionFormGroupInput for create.
 */
type ContractDivisionFormGroupInput = IContractDivision | PartialWithRequiredKeyOf<NewContractDivision>;

type ContractDivisionFormDefaults = Pick<NewContractDivision, 'id' | 'completed'>;

type ContractDivisionFormGroupContent = {
  id: FormControl<IContractDivision['id'] | NewContractDivision['id']>;
  contractDivisionId: FormControl<IContractDivision['contractDivisionId']>;
  contractDivisionNumber: FormControl<IContractDivision['contractDivisionNumber']>;
  companyId: FormControl<IContractDivision['companyId']>;
  buildSmartReference: FormControl<IContractDivision['buildSmartReference']>;
  shiftStart: FormControl<IContractDivision['shiftStart']>;
  shiftEnd: FormControl<IContractDivision['shiftEnd']>;
  type: FormControl<IContractDivision['type']>;
  completed: FormControl<IContractDivision['completed']>;
};

export type ContractDivisionFormGroup = FormGroup<ContractDivisionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContractDivisionFormService {
  createContractDivisionFormGroup(contractDivision: ContractDivisionFormGroupInput = { id: null }): ContractDivisionFormGroup {
    const contractDivisionRawValue = {
      ...this.getFormDefaults(),
      ...contractDivision,
    };
    return new FormGroup<ContractDivisionFormGroupContent>({
      id: new FormControl(
        { value: contractDivisionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      contractDivisionId: new FormControl(contractDivisionRawValue.contractDivisionId),
      contractDivisionNumber: new FormControl(contractDivisionRawValue.contractDivisionNumber),
      companyId: new FormControl(contractDivisionRawValue.companyId),
      buildSmartReference: new FormControl(contractDivisionRawValue.buildSmartReference),
      shiftStart: new FormControl(contractDivisionRawValue.shiftStart),
      shiftEnd: new FormControl(contractDivisionRawValue.shiftEnd),
      type: new FormControl(contractDivisionRawValue.type),
      completed: new FormControl(contractDivisionRawValue.completed),
    });
  }

  getContractDivision(form: ContractDivisionFormGroup): IContractDivision | NewContractDivision {
    return form.getRawValue() as IContractDivision | NewContractDivision;
  }

  resetForm(form: ContractDivisionFormGroup, contractDivision: ContractDivisionFormGroupInput): void {
    const contractDivisionRawValue = { ...this.getFormDefaults(), ...contractDivision };
    form.reset(
      {
        ...contractDivisionRawValue,
        id: { value: contractDivisionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ContractDivisionFormDefaults {
    return {
      id: null,
      completed: false,
    };
  }
}
