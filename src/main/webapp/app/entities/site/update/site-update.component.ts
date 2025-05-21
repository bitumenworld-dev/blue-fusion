import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISite } from '../site.model';
import { SiteService } from '../service/site.service';
import { SiteFormGroup, SiteFormService } from './site-form.service';
import { CompanyEntityArrayResponseType, CompanyService } from '../../company/service/company.service';
import { ICompany } from '../../company/company.model';

@Component({
  selector: 'jhi-site-update',
  templateUrl: './site-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SiteUpdateComponent implements OnInit {
  isSaving = false;
  site: ISite | null = null;

  protected siteService = inject(SiteService);
  protected siteFormService = inject(SiteFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected readonly companyService = inject(CompanyService);

  editForm: SiteFormGroup = this.siteFormService.createSiteFormGroup();

  // Company array dummy
  companiesArray: ICompany[] | null = [];
  searchTerm = '';
  // @ts-ignore
  filteredCompanies = [...this.companiesArray];
  showDropdown = false;

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ site }) => {
      this.site = site;
      this.loadCompanies();
      if (site) {
        this.updateForm(site);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const site = this.siteFormService.getSite(this.editForm);
    if (site.id !== null) {
      this.subscribeToSaveResponse(this.siteService.update(site));
    } else {
      this.subscribeToSaveResponse(this.siteService.create(site));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISite>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: (response: HttpResponse<ISite>) => {
        const savedSite: ISite | null = response.body;
        this.onSaveSuccess(savedSite);
      },
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(result: ISite | null): void {
    if (result) {
      this.siteService.uploadImage(result);
    }
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(site: ISite): void {
    this.site = site;
    this.siteFormService.resetForm(this.editForm, site);

    const selectedCompanyId = site.companyId ?? site.company;
    if (site.company) {
      this.editForm.get('companyId')?.setValue(site.companyId);
      this.searchTerm = site.company;
    }
  }

  // Company Filtering & Selection Logic
  loadCompanies(): void {
    this.companyService.query().subscribe({
      next: (res: CompanyEntityArrayResponseType) => {
        this.companiesArray = res.body;
      },
    });
  }

  filterCompanies(): void {
    const term = this.searchTerm.toLowerCase();
    // @ts-ignore
    this.filteredCompanies = this.companiesArray.filter(company => company.name.toLowerCase().includes(term));
  }

  selectCompany(company: { id: number; name: string }): void {
    this.searchTerm = company.name;
    this.editForm.get('companyId')?.setValue(company.id);
    this.showDropdown = false;
  }

  hideDropdown(): void {
    setTimeout(() => {
      this.showDropdown = false;
    }, 200);
  }
}
