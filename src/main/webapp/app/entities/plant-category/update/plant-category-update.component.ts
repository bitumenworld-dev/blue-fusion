import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPlantCategory } from '../plant-category.model';
import { PlantCategoryService } from '../service/plant-category.service';
import { PlantCategoryFormGroup, PlantCategoryFormService } from './plant-category-form.service';

@Component({
  selector: 'jhi-plant-category-update',
  templateUrl: './plant-category-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PlantCategoryUpdateComponent implements OnInit {
  isSaving = false;
  plantCategory: IPlantCategory | null = null;

  protected plantCategoryService = inject(PlantCategoryService);
  protected plantCategoryFormService = inject(PlantCategoryFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PlantCategoryFormGroup = this.plantCategoryFormService.createPlantCategoryFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plantCategory }) => {
      this.plantCategory = plantCategory;
      if (plantCategory) {
        this.updateForm(plantCategory);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const plantCategory = this.plantCategoryFormService.getPlantCategory(this.editForm);
    if (plantCategory.id !== null) {
      this.subscribeToSaveResponse(this.plantCategoryService.update(plantCategory));
    } else {
      this.subscribeToSaveResponse(this.plantCategoryService.create(plantCategory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlantCategory>>): void {
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

  protected updateForm(plantCategory: IPlantCategory): void {
    this.plantCategory = plantCategory;
    this.plantCategoryFormService.resetForm(this.editForm, plantCategory);
  }
}
