import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { DivisionType } from 'app/entities/enumerations/division-type.model';
import { IContractDivision } from '../contract-division.model';
import { ContractDivisionService } from '../service/contract-division.service';
import { ContractDivisionFormGroup, ContractDivisionFormService } from './contract-division-form.service';

@Component({
  selector: 'jhi-contract-division-update',
  templateUrl: './contract-division-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ContractDivisionUpdateComponent implements OnInit {
  isSaving = false;
  contractDivision: IContractDivision | null = null;
  divisionTypeValues = Object.keys(DivisionType);

  protected contractDivisionService = inject(ContractDivisionService);
  protected contractDivisionFormService = inject(ContractDivisionFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ContractDivisionFormGroup = this.contractDivisionFormService.createContractDivisionFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contractDivision }) => {
      this.contractDivision = contractDivision;
      if (contractDivision) {
        this.updateForm(contractDivision);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contractDivision = this.contractDivisionFormService.getContractDivision(this.editForm);
    if (contractDivision.id !== null) {
      this.subscribeToSaveResponse(this.contractDivisionService.update(contractDivision));
    } else {
      this.subscribeToSaveResponse(this.contractDivisionService.create(contractDivision));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContractDivision>>): void {
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

  protected updateForm(contractDivision: IContractDivision): void {
    this.contractDivision = contractDivision;
    this.contractDivisionFormService.resetForm(this.editForm, contractDivision);
  }
}
