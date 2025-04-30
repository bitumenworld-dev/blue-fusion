import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ServiceUnit } from 'app/entities/enumerations/service-unit.model';
import { IAssetPlantServiceReading } from '../asset-plant-service-reading.model';
import { AssetPlantServiceReadingService } from '../service/asset-plant-service-reading.service';
import { AssetPlantServiceReadingFormGroup, AssetPlantServiceReadingFormService } from './asset-plant-service-reading-form.service';

@Component({
  selector: 'jhi-asset-plant-service-reading-update',
  templateUrl: './asset-plant-service-reading-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AssetPlantServiceReadingUpdateComponent implements OnInit {
  isSaving = false;
  assetPlantServiceReading: IAssetPlantServiceReading | null = null;
  serviceUnitValues = Object.keys(ServiceUnit);

  protected assetPlantServiceReadingService = inject(AssetPlantServiceReadingService);
  protected assetPlantServiceReadingFormService = inject(AssetPlantServiceReadingFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AssetPlantServiceReadingFormGroup = this.assetPlantServiceReadingFormService.createAssetPlantServiceReadingFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assetPlantServiceReading }) => {
      this.assetPlantServiceReading = assetPlantServiceReading;
      if (assetPlantServiceReading) {
        this.updateForm(assetPlantServiceReading);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const assetPlantServiceReading = this.assetPlantServiceReadingFormService.getAssetPlantServiceReading(this.editForm);
    if (assetPlantServiceReading.id !== null) {
      this.subscribeToSaveResponse(this.assetPlantServiceReadingService.update(assetPlantServiceReading));
    } else {
      this.subscribeToSaveResponse(this.assetPlantServiceReadingService.create(assetPlantServiceReading));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssetPlantServiceReading>>): void {
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

  protected updateForm(assetPlantServiceReading: IAssetPlantServiceReading): void {
    this.assetPlantServiceReading = assetPlantServiceReading;
    this.assetPlantServiceReadingFormService.resetForm(this.editForm, assetPlantServiceReading);
  }
}
