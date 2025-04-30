import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IWorkshop } from '../workshop.model';
import { WorkshopService } from '../service/workshop.service';
import { WorkshopFormGroup, WorkshopFormService } from './workshop-form.service';

@Component({
  selector: 'jhi-workshop-update',
  templateUrl: './workshop-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class WorkshopUpdateComponent implements OnInit {
  isSaving = false;
  workshop: IWorkshop | null = null;

  protected workshopService = inject(WorkshopService);
  protected workshopFormService = inject(WorkshopFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: WorkshopFormGroup = this.workshopFormService.createWorkshopFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workshop }) => {
      this.workshop = workshop;
      if (workshop) {
        this.updateForm(workshop);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workshop = this.workshopFormService.getWorkshop(this.editForm);
    if (workshop.id !== null) {
      this.subscribeToSaveResponse(this.workshopService.update(workshop));
    } else {
      this.subscribeToSaveResponse(this.workshopService.create(workshop));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkshop>>): void {
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

  protected updateForm(workshop: IWorkshop): void {
    this.workshop = workshop;
    this.workshopFormService.resetForm(this.editForm, workshop);
  }
}
