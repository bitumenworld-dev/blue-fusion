import { Component, ElementRef, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { AssetPlantPhotoLabel } from 'app/entities/enumerations/asset-plant-photo-label.model';
import { AssetPlantPhotoService } from '../service/asset-plant-photo.service';
import { IAssetPlantPhoto } from '../asset-plant-photo.model';
import { AssetPlantPhotoFormGroup, AssetPlantPhotoFormService } from './asset-plant-photo-form.service';

@Component({
  selector: 'jhi-asset-plant-photo-update',
  templateUrl: './asset-plant-photo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AssetPlantPhotoUpdateComponent implements OnInit {
  isSaving = false;
  assetPlantPhoto: IAssetPlantPhoto | null = null;
  assetPlantPhotoLabelValues = Object.keys(AssetPlantPhotoLabel);

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected assetPlantPhotoService = inject(AssetPlantPhotoService);
  protected assetPlantPhotoFormService = inject(AssetPlantPhotoFormService);
  protected elementRef = inject(ElementRef);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AssetPlantPhotoFormGroup = this.assetPlantPhotoFormService.createAssetPlantPhotoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assetPlantPhoto }) => {
      this.assetPlantPhoto = assetPlantPhoto;
      if (assetPlantPhoto) {
        this.updateForm(assetPlantPhoto);
      }
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('blueFusionApp.error', { ...err, key: `error.file.${err.key}` })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector(`#${idInput}`)) {
      this.elementRef.nativeElement.querySelector(`#${idInput}`).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const assetPlantPhoto = this.assetPlantPhotoFormService.getAssetPlantPhoto(this.editForm);
    if (assetPlantPhoto.id !== null) {
      this.subscribeToSaveResponse(this.assetPlantPhotoService.update(assetPlantPhoto));
    } else {
      this.subscribeToSaveResponse(this.assetPlantPhotoService.create(assetPlantPhoto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssetPlantPhoto>>): void {
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

  protected updateForm(assetPlantPhoto: IAssetPlantPhoto): void {
    this.assetPlantPhoto = assetPlantPhoto;
    this.assetPlantPhotoFormService.resetForm(this.editForm, assetPlantPhoto);
  }
}
