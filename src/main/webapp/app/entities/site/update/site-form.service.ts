import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ISite, NewSite } from '../site.model';

type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

type SiteFormGroupInput = ISite | PartialWithRequiredKeyOf<NewSite>;

type SiteFormDefaults = Pick<NewSite, 'id' | 'isActive'>;

type SiteFormGroupContent = {
  id: FormControl<ISite['id'] | NewSite['id']>;
  siteName: FormControl<ISite['siteName']>;
  latitude: FormControl<ISite['latitude']>;
  longitude: FormControl<ISite['longitude']>;
  isActive: FormControl<ISite['isActive']>;
  siteNotes: FormControl<ISite['siteNotes']>;
  siteImage: FormControl<ISite['siteImage']>;
  companyId: FormControl<ISite['companyId']>;
};

export type SiteFormGroup = FormGroup<SiteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SiteFormService {
  createSiteFormGroup(site: SiteFormGroupInput = { id: null }): SiteFormGroup {
    const siteRawValue = {
      ...this.getFormDefaults(),
      ...site,
    };
    return new FormGroup<SiteFormGroupContent>({
      id: new FormControl(
        { value: siteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      siteName: new FormControl(siteRawValue.siteName),
      latitude: new FormControl(siteRawValue.latitude),
      longitude: new FormControl(siteRawValue.longitude),
      isActive: new FormControl(siteRawValue.isActive),
      siteNotes: new FormControl(siteRawValue.siteNotes),
      siteImage: new FormControl(siteRawValue.siteImage),
      companyId: new FormControl(siteRawValue.companyId ?? null),
    });
  }

  getSite(form: SiteFormGroup): ISite | NewSite {
    return form.getRawValue() as ISite | NewSite;
  }

  resetForm(form: SiteFormGroup, site: SiteFormGroupInput): void {
    const siteRawValue = { ...this.getFormDefaults(), ...site };
    form.reset({
      ...siteRawValue,
      id: { value: siteRawValue.id, disabled: true },
    } as any);
  }

  private getFormDefaults(): SiteFormDefaults {
    return {
      id: null,
      isActive: false,
    };
  }
}
