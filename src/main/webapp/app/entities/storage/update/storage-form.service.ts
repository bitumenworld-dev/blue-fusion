import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { Storage, NewStorage } from '../storage.model';
import { ISite, NewSite } from '../../site/site.model';
import { SiteFormGroup } from '../../site/update/site-form.service';

type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };
type StorageFormGroupInput = Storage | PartialWithRequiredKeyOf<NewStorage>;

type StorageFormDefaults = Pick<NewStorage, 'id' | 'isActive'>;

type StorageFormGroupContent = {
  id: FormControl<Storage['id'] | NewStorage['id']>;
  storageCode: FormControl<NewStorage['storageCode']>;
  buildSmartCode: FormControl<NewStorage['buildSmartCode']>;
  companyId: FormControl<number | null>;
  siteId: FormControl<number | null>;
  name: FormControl<NewStorage['name']>;
  storageContent: FormControl<NewStorage['storageContent']>;
  capacity: FormControl<NewStorage['capacity']>;
  accessKey: FormControl<NewStorage['accessKey']>;
  isActive: FormControl<NewStorage['isActive']>;
  isFixed: FormControl<NewStorage['isFixed']>;
};

export type StorageFormGroup = FormGroup<StorageFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StorageFormService {
  createStorageFormGroup(storage: StorageFormGroupInput = { id: null }): StorageFormGroup {
    const storageRawValue = {
      ...this.getFormDefaults(),
      ...storage,
    };

    return new FormGroup<StorageFormGroupContent>({
      id: new FormControl(
        { value: storageRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      storageCode: new FormControl(storageRawValue.storageCode),
      buildSmartCode: new FormControl(storageRawValue.buildSmartCode),
      isActive: new FormControl(storageRawValue.isActive),
      name: new FormControl(storageRawValue.name),
      storageContent: new FormControl(storageRawValue.storageContent),
      companyId: new FormControl(storageRawValue.companyId ?? null),
      siteId: new FormControl(storageRawValue.siteId ?? null),
      capacity: new FormControl(storageRawValue.capacity),
      accessKey: new FormControl(storageRawValue.accessKey),
      isFixed: new FormControl(storageRawValue.isFixed),
    });
  }

  getStorage(form: StorageFormGroup): Storage | NewStorage {
    return form.getRawValue() as Storage | NewStorage;
  }

  resetForm(form: StorageFormGroup, storage: StorageFormGroupInput): void {
    const storageRawValue = { ...this.getFormDefaults(), ...storage };
    form.reset({
      ...storageRawValue,
      id: { value: storageRawValue.id, disabled: true },
    } as any);
  }

  private getFormDefaults(): StorageFormDefaults {
    return {
      id: null,
      isActive: true,
    };
  }
}
