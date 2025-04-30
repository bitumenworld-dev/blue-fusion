import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISiteContract } from '../site-contract.model';
import { SiteContractService } from '../service/site-contract.service';
import { SiteContractFormGroup, SiteContractFormService } from './site-contract-form.service';

@Component({
  selector: 'jhi-site-contract-update',
  templateUrl: './site-contract-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SiteContractUpdateComponent implements OnInit {
  isSaving = false;
  siteContract: ISiteContract | null = null;

  protected siteContractService = inject(SiteContractService);
  protected siteContractFormService = inject(SiteContractFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SiteContractFormGroup = this.siteContractFormService.createSiteContractFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ siteContract }) => {
      this.siteContract = siteContract;
      if (siteContract) {
        this.updateForm(siteContract);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const siteContract = this.siteContractFormService.getSiteContract(this.editForm);
    if (siteContract.id !== null) {
      this.subscribeToSaveResponse(this.siteContractService.update(siteContract));
    } else {
      this.subscribeToSaveResponse(this.siteContractService.create(siteContract));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISiteContract>>): void {
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

  protected updateForm(siteContract: ISiteContract): void {
    this.siteContract = siteContract;
    this.siteContractFormService.resetForm(this.editForm, siteContract);
  }
}
