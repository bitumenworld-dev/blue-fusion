import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { DriverOrOperator } from 'app/entities/enumerations/driver-or-operator.model';
import { HorseOrTrailer } from 'app/entities/enumerations/horse-or-trailer.model';
import { SMRReaderType } from 'app/entities/enumerations/smr-reader-type.model';
import { FuelType } from 'app/entities/enumerations/fuel-type.model';
import { ConsumptionUnit } from 'app/entities/enumerations/consumption-unit.model';
import { PlantHoursStatus } from 'app/entities/enumerations/plant-hours-status.model';
import { AssetPlantService } from '../service/asset-plant.service';
import { IAssetPlant } from '../asset-plant.model';
import { AssetPlantFormGroup, AssetPlantFormService } from './asset-plant-form.service';

@Component({
  selector: 'jhi-asset-plant-update',
  templateUrl: './asset-plant-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AssetPlantUpdateComponent implements OnInit {
  isSaving = false;
  assetPlant: IAssetPlant | null = null;
  driverOrOperatorValues = Object.keys(DriverOrOperator);
  horseOrTrailerValues = Object.keys(HorseOrTrailer);
  sMRReaderTypeValues = Object.keys(SMRReaderType);
  fuelTypeValues = Object.keys(FuelType);
  consumptionUnitValues = Object.keys(ConsumptionUnit);
  plantHoursStatusValues = Object.keys(PlantHoursStatus);

  protected assetPlantService = inject(AssetPlantService);
  protected assetPlantFormService = inject(AssetPlantFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AssetPlantFormGroup = this.assetPlantFormService.createAssetPlantFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assetPlant }) => {
      this.assetPlant = assetPlant;
      if (assetPlant) {
        this.updateForm(assetPlant);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const assetPlant = this.assetPlantFormService.getAssetPlant(this.editForm);
    if (assetPlant.id !== null) {
      this.subscribeToSaveResponse(this.assetPlantService.update(assetPlant));
    } else {
      this.subscribeToSaveResponse(this.assetPlantService.create(assetPlant));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssetPlant>>): void {
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

  protected updateForm(assetPlant: IAssetPlant): void {
    this.assetPlant = assetPlant;
    this.assetPlantFormService.resetForm(this.editForm, assetPlant);
  }
}
