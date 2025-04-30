import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFuelTransactionLine } from '../fuel-transaction-line.model';
import { FuelTransactionLineService } from '../service/fuel-transaction-line.service';
import { FuelTransactionLineFormGroup, FuelTransactionLineFormService } from './fuel-transaction-line-form.service';

@Component({
  selector: 'jhi-fuel-transaction-line-update',
  templateUrl: './fuel-transaction-line-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FuelTransactionLineUpdateComponent implements OnInit {
  isSaving = false;
  fuelTransactionLine: IFuelTransactionLine | null = null;

  protected fuelTransactionLineService = inject(FuelTransactionLineService);
  protected fuelTransactionLineFormService = inject(FuelTransactionLineFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FuelTransactionLineFormGroup = this.fuelTransactionLineFormService.createFuelTransactionLineFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fuelTransactionLine }) => {
      this.fuelTransactionLine = fuelTransactionLine;
      if (fuelTransactionLine) {
        this.updateForm(fuelTransactionLine);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fuelTransactionLine = this.fuelTransactionLineFormService.getFuelTransactionLine(this.editForm);
    if (fuelTransactionLine.id !== null) {
      this.subscribeToSaveResponse(this.fuelTransactionLineService.update(fuelTransactionLine));
    } else {
      this.subscribeToSaveResponse(this.fuelTransactionLineService.create(fuelTransactionLine));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuelTransactionLine>>): void {
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

  protected updateForm(fuelTransactionLine: IFuelTransactionLine): void {
    this.fuelTransactionLine = fuelTransactionLine;
    this.fuelTransactionLineFormService.resetForm(this.editForm, fuelTransactionLine);
  }
}
