import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { FuelType } from 'app/entities/enumerations/fuel-type.model';
import { FuelTransaction } from '../fuel-transaction.model';
import { FuelTransactionService } from '../service/fuel-transaction.service';
import { FuelTransactionHeaderFormGroup, FuelTransactionFormService } from './fuel-transaction-form.service';

@Component({
  templateUrl: './fuel-transaction-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FuelTransactionUpdateComponent implements OnInit {
  isSaving = false;
  fuelTransactionHeader: FuelTransaction | null = null;
  fuelTypeValues = Object.keys(FuelType);

  protected fuelTransactionHeaderService = inject(FuelTransactionService);
  protected fuelTransactionHeaderFormService = inject(FuelTransactionFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FuelTransactionHeaderFormGroup = this.fuelTransactionHeaderFormService.createFuelTransactionHeaderFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fuelTransactionHeader }) => {
      this.fuelTransactionHeader = fuelTransactionHeader;
      if (fuelTransactionHeader) {
        this.updateForm(fuelTransactionHeader);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fuelTransactionHeader = this.fuelTransactionHeaderFormService.getFuelTransactionHeader(this.editForm);
    if (fuelTransactionHeader.id !== null) {
      this.subscribeToSaveResponse(this.fuelTransactionHeaderService.update(fuelTransactionHeader));
    } else {
      this.subscribeToSaveResponse(this.fuelTransactionHeaderService.create(fuelTransactionHeader));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<FuelTransaction>>): void {
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

  protected updateForm(fuelTransactionHeader: FuelTransaction): void {
    this.fuelTransactionHeader = fuelTransactionHeader;
    this.fuelTransactionHeaderFormService.resetForm(this.editForm, fuelTransactionHeader);
  }
}
