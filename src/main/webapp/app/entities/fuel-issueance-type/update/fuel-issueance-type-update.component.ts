import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFuelIssueanceType } from '../fuel-issueance-type.model';
import { FuelIssueanceTypeService } from '../service/fuel-issueance-type.service';
import { FuelIssueanceTypeFormGroup, FuelIssueanceTypeFormService } from './fuel-issueance-type-form.service';

@Component({
  selector: 'jhi-fuel-issueance-type-update',
  templateUrl: './fuel-issueance-type-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FuelIssueanceTypeUpdateComponent implements OnInit {
  isSaving = false;
  fuelIssueanceType: IFuelIssueanceType | null = null;

  protected fuelIssueanceTypeService = inject(FuelIssueanceTypeService);
  protected fuelIssueanceTypeFormService = inject(FuelIssueanceTypeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FuelIssueanceTypeFormGroup = this.fuelIssueanceTypeFormService.createFuelIssueanceTypeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fuelIssueanceType }) => {
      this.fuelIssueanceType = fuelIssueanceType;
      if (fuelIssueanceType) {
        this.updateForm(fuelIssueanceType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fuelIssueanceType = this.fuelIssueanceTypeFormService.getFuelIssueanceType(this.editForm);
    if (fuelIssueanceType.id !== null) {
      this.subscribeToSaveResponse(this.fuelIssueanceTypeService.update(fuelIssueanceType));
    } else {
      this.subscribeToSaveResponse(this.fuelIssueanceTypeService.create(fuelIssueanceType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuelIssueanceType>>): void {
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

  protected updateForm(fuelIssueanceType: IFuelIssueanceType): void {
    this.fuelIssueanceType = fuelIssueanceType;
    this.fuelIssueanceTypeFormService.resetForm(this.editForm, fuelIssueanceType);
  }
}
