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

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SiteFormGroup = this.siteFormService.createSiteFormGroup();

  // Company array dummy
  companiesArray = [
    { id: 1, name: 'Acme Corp' },
    { id: 2, name: 'Globex Inc' },
    { id: 3, name: 'Initech' },
  ];

  searchTerm = '';
  filteredCompanies = [...this.companiesArray];
  showDropdown = false;

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ site }) => {
      this.site = site;
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
  protected updateForm(site: ISite): void {
    this.site = site;
    this.siteFormService.resetForm(this.editForm, site);

    // Set the searchTerm for displaying selected company name in input
    const selectedCompanyId = site.companyId ?? site.company;
    const selectedCompany = this.companiesArray.find(c => c.id === selectedCompanyId);
    this.searchTerm = this.companiesArray.find(c => c.id === site.companyId)?.name ?? '';
  }

  // Company Filtering & Selection Logic
  filterCompanies(): void {
    const term = this.searchTerm.toLowerCase();
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
