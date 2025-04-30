import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IManufacturerModel } from '../manufacturer-model.model';
import { ManufacturerModelService } from '../service/manufacturer-model.service';
import { ManufacturerModelFormGroup, ManufacturerModelFormService } from './manufacturer-model-form.service';

@Component({
  selector: 'jhi-manufacturer-model-update',
  templateUrl: './manufacturer-model-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ManufacturerModelUpdateComponent implements OnInit {
  isSaving = false;
  manufacturerModel: IManufacturerModel | null = null;

  protected manufacturerModelService = inject(ManufacturerModelService);
  protected manufacturerModelFormService = inject(ManufacturerModelFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ManufacturerModelFormGroup = this.manufacturerModelFormService.createManufacturerModelFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ manufacturerModel }) => {
      this.manufacturerModel = manufacturerModel;
      if (manufacturerModel) {
        this.updateForm(manufacturerModel);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const manufacturerModel = this.manufacturerModelFormService.getManufacturerModel(this.editForm);
    if (manufacturerModel.id !== null) {
      this.subscribeToSaveResponse(this.manufacturerModelService.update(manufacturerModel));
    } else {
      this.subscribeToSaveResponse(this.manufacturerModelService.create(manufacturerModel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IManufacturerModel>>): void {
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

  protected updateForm(manufacturerModel: IManufacturerModel): void {
    this.manufacturerModel = manufacturerModel;
    this.manufacturerModelFormService.resetForm(this.editForm, manufacturerModel);
  }
}
