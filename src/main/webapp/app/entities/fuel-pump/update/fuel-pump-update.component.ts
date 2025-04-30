import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFuelPump } from '../fuel-pump.model';
import { FuelPumpService } from '../service/fuel-pump.service';
import { FuelPumpFormGroup, FuelPumpFormService } from './fuel-pump-form.service';

@Component({
  selector: 'jhi-fuel-pump-update',
  templateUrl: './fuel-pump-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FuelPumpUpdateComponent implements OnInit {
  isSaving = false;
  fuelPump: IFuelPump | null = null;

  protected fuelPumpService = inject(FuelPumpService);
  protected fuelPumpFormService = inject(FuelPumpFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FuelPumpFormGroup = this.fuelPumpFormService.createFuelPumpFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fuelPump }) => {
      this.fuelPump = fuelPump;
      if (fuelPump) {
        this.updateForm(fuelPump);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fuelPump = this.fuelPumpFormService.getFuelPump(this.editForm);
    if (fuelPump.id !== null) {
      this.subscribeToSaveResponse(this.fuelPumpService.update(fuelPump));
    } else {
      this.subscribeToSaveResponse(this.fuelPumpService.create(fuelPump));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuelPump>>): void {
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

  protected updateForm(fuelPump: IFuelPump): void {
    this.fuelPump = fuelPump;
    this.fuelPumpFormService.resetForm(this.editForm, fuelPump);
  }
}
