import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFuelTransactionType } from '../fuel-transaction-type.model';
import { FuelTransactionTypeService } from '../service/fuel-transaction-type.service';
import { FuelTransactionTypeFormGroup, FuelTransactionTypeFormService } from './fuel-transaction-type-form.service';

@Component({
  selector: 'jhi-fuel-transaction-type-update',
  templateUrl: './fuel-transaction-type-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FuelTransactionTypeUpdateComponent implements OnInit {
  isSaving = false;
  fuelTransactionType: IFuelTransactionType | null = null;

  protected fuelTransactionTypeService = inject(FuelTransactionTypeService);
  protected fuelTransactionTypeFormService = inject(FuelTransactionTypeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FuelTransactionTypeFormGroup = this.fuelTransactionTypeFormService.createFuelTransactionTypeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fuelTransactionType }) => {
      this.fuelTransactionType = fuelTransactionType;
      if (fuelTransactionType) {
        this.updateForm(fuelTransactionType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fuelTransactionType = this.fuelTransactionTypeFormService.getFuelTransactionType(this.editForm);
    if (fuelTransactionType.id !== null) {
      this.subscribeToSaveResponse(this.fuelTransactionTypeService.update(fuelTransactionType));
    } else {
      this.subscribeToSaveResponse(this.fuelTransactionTypeService.create(fuelTransactionType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuelTransactionType>>): void {
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

  protected updateForm(fuelTransactionType: IFuelTransactionType): void {
    this.fuelTransactionType = fuelTransactionType;
    this.fuelTransactionTypeFormService.resetForm(this.editForm, fuelTransactionType);
  }
}
