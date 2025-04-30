import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPlantSubcategory } from '../plant-subcategory.model';
import { PlantSubcategoryService } from '../service/plant-subcategory.service';
import { PlantSubcategoryFormGroup, PlantSubcategoryFormService } from './plant-subcategory-form.service';

@Component({
  selector: 'jhi-plant-subcategory-update',
  templateUrl: './plant-subcategory-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PlantSubcategoryUpdateComponent implements OnInit {
  isSaving = false;
  plantSubcategory: IPlantSubcategory | null = null;

  protected plantSubcategoryService = inject(PlantSubcategoryService);
  protected plantSubcategoryFormService = inject(PlantSubcategoryFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PlantSubcategoryFormGroup = this.plantSubcategoryFormService.createPlantSubcategoryFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plantSubcategory }) => {
      this.plantSubcategory = plantSubcategory;
      if (plantSubcategory) {
        this.updateForm(plantSubcategory);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const plantSubcategory = this.plantSubcategoryFormService.getPlantSubcategory(this.editForm);
    if (plantSubcategory.id !== null) {
      this.subscribeToSaveResponse(this.plantSubcategoryService.update(plantSubcategory));
    } else {
      this.subscribeToSaveResponse(this.plantSubcategoryService.create(plantSubcategory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlantSubcategory>>): void {
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

  protected updateForm(plantSubcategory: IPlantSubcategory): void {
    this.plantSubcategory = plantSubcategory;
    this.plantSubcategoryFormService.resetForm(this.editForm, plantSubcategory);
  }
}
