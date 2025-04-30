import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ISiteContract, NewSiteContract } from '../site-contract.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISiteContract for edit and NewSiteContractFormGroupInput for create.
 */
type SiteContractFormGroupInput = ISiteContract | PartialWithRequiredKeyOf<NewSiteContract>;

type SiteContractFormDefaults = Pick<NewSiteContract, 'id'>;

type SiteContractFormGroupContent = {
  id: FormControl<ISiteContract['id'] | NewSiteContract['id']>;
  siteContractId: FormControl<ISiteContract['siteContractId']>;
  siteId: FormControl<ISiteContract['siteId']>;
  contractId: FormControl<ISiteContract['contractId']>;
};

export type SiteContractFormGroup = FormGroup<SiteContractFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SiteContractFormService {
  createSiteContractFormGroup(siteContract: SiteContractFormGroupInput = { id: null }): SiteContractFormGroup {
    const siteContractRawValue = {
      ...this.getFormDefaults(),
      ...siteContract,
    };
    return new FormGroup<SiteContractFormGroupContent>({
      id: new FormControl(
        { value: siteContractRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      siteContractId: new FormControl(siteContractRawValue.siteContractId),
      siteId: new FormControl(siteContractRawValue.siteId),
      contractId: new FormControl(siteContractRawValue.contractId),
    });
  }

  getSiteContract(form: SiteContractFormGroup): ISiteContract | NewSiteContract {
    return form.getRawValue() as ISiteContract | NewSiteContract;
  }

  resetForm(form: SiteContractFormGroup, siteContract: SiteContractFormGroupInput): void {
    const siteContractRawValue = { ...this.getFormDefaults(), ...siteContract };
    form.reset(
      {
        ...siteContractRawValue,
        id: { value: siteContractRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SiteContractFormDefaults {
    return {
      id: null,
    };
  }
}
