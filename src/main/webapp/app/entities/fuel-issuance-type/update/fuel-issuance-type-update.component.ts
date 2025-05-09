import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFuelIssuanceType } from '../fuel-issuance-type.model';
import { FuelIssuanceTypeService } from '../service/fuel-issuance-type.service';
import { FuelIssuanceTypeFormGroup, FuelIssuanceTypeFormService } from './fuel-issuance-type-form.service';

@Component({
  selector: 'jhi-fuel-issuance-type-update',
  templateUrl: './fuel-issuance-type-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FuelIssuanceTypeUpdateComponent implements OnInit {
  isSaving = false;
  fuelIssuanceType: IFuelIssuanceType | null = null;

  protected fuelIssuanceTypeService = inject(FuelIssuanceTypeService);
  protected fuelIssuanceTypeFormService = inject(FuelIssuanceTypeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FuelIssuanceTypeFormGroup = this.fuelIssuanceTypeFormService.createFuelIssuanceTypeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fuelIssuanceType }) => {
      this.fuelIssuanceType = fuelIssuanceType;
      if (fuelIssuanceType) {
        this.updateForm(fuelIssuanceType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fuelIssuanceType = this.fuelIssuanceTypeFormService.getFuelIssuanceType(this.editForm);
    if (fuelIssuanceType.id !== null) {
      this.subscribeToSaveResponse(this.fuelIssuanceTypeService.update(fuelIssuanceType));
    } else {
      this.subscribeToSaveResponse(this.fuelIssuanceTypeService.create(fuelIssuanceType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuelIssuanceType>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(fuelIssuanceType: IFuelIssuanceType): void {
    this.fuelIssuanceType = fuelIssuanceType;
    this.fuelIssuanceTypeFormService.resetForm(this.editForm, fuelIssuanceType);
  }
}
