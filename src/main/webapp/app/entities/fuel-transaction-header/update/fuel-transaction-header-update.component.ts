import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { FuelType } from 'app/entities/enumerations/fuel-type.model';
import { IFuelTransactionHeader } from '../fuel-transaction-header.model';
import { FuelTransactionHeaderService } from '../service/fuel-transaction-header.service';
import { FuelTransactionHeaderFormGroup, FuelTransactionHeaderFormService } from './fuel-transaction-header-form.service';

@Component({
  selector: 'jhi-fuel-transaction-header-update',
  templateUrl: './fuel-transaction-header-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FuelTransactionHeaderUpdateComponent implements OnInit {
  isSaving = false;
  fuelTransactionHeader: IFuelTransactionHeader | null = null;
  fuelTypeValues = Object.keys(FuelType);

  protected fuelTransactionHeaderService = inject(FuelTransactionHeaderService);
  protected fuelTransactionHeaderFormService = inject(FuelTransactionHeaderFormService);
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuelTransactionHeader>>): void {
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

  protected updateForm(fuelTransactionHeader: IFuelTransactionHeader): void {
    this.fuelTransactionHeader = fuelTransactionHeader;
    this.fuelTransactionHeaderFormService.resetForm(this.editForm, fuelTransactionHeader);
  }
}
