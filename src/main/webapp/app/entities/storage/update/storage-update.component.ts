import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { Storage } from '../storage.model';
import { StorageService } from '../service/storage.service';
import { StorageFormGroup, StorageFormService } from './storage-form.service';

@Component({
  templateUrl: './storage-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class StorageUpdateComponent implements OnInit {
  isSaving = false;
  storage: Storage | null = null;

  protected storageService = inject(StorageService);
  protected storageFormService = inject(StorageFormService);
  protected activatedRoute = inject(ActivatedRoute);

  editForm: StorageFormGroup = this.storageFormService.createStorageFormGroup();

  //TODO: remove  arrays and bind to endpoints
  companiesArray = [
    { id: 1, name: 'Acme Corp' },
    { id: 2, name: 'Globex Inc' },
    { id: 3, name: 'Initech' },
  ];
  siteArray = [
    { id: 1, name: 'Msasa HQ' },
    { id: 2, name: 'New Parliament' },
    { id: 3, name: 'Initech' },
  ];

  companySearchTerm = '';
  siteSearchTerm = '';
  filteredCompanies = [...this.companiesArray];
  filteredSites = [...this.siteArray];
  showCompaniesDropdown = false;
  showSitesDropdown = false;

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ storage }) => {
      this.storage = storage;
      if (storage) {
        this.updateForm(storage);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const storage = this.storageFormService.getStorage(this.editForm);
    if (storage.id !== null) {
      this.subscribeToSaveResponse(this.storageService.update(storage));
    } else {
      this.subscribeToSaveResponse(this.storageService.create(storage));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<Storage>>): void {
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

  protected updateForm(storage: Storage): void {
    this.storage = storage;
    this.storageFormService.resetForm(this.editForm, storage);

    // Set the searchTerm for displaying selected company name in input
    const selectedCompanyId = storage.companyId ?? storage.company;
    const selectedCompany = this.companiesArray.find(c => c.id === selectedCompanyId);
    this.companySearchTerm = this.companiesArray.find(c => c.id === storage.companyId)?.name ?? '';

    // Set the searchTerm for displaying selected site name in input
    const selectedSiteId = storage.siteId ?? storage.company;
    const selectedSite = this.siteArray.find(c => c.id === selectedSiteId);
    this.siteSearchTerm = this.siteArray.find(c => c.id === storage.siteId)?.name ?? '';
  }

  // Company Filtering & Selection Logic
  filterCompanies(): void {
    const term = this.companySearchTerm.toLowerCase();
    this.filteredCompanies = this.companiesArray.filter(company => company.name.toLowerCase().includes(term));
  }

  selectCompany(company: { id: number; name: string }): void {
    this.companySearchTerm = company.name;
    this.editForm.get('companyId')?.setValue(company.id);
    this.showCompaniesDropdown = false;
  }

  hideCompanyDropdown(): void {
    setTimeout(() => {
      this.showCompaniesDropdown = false;
    }, 200);
  }

  filterSites(): void {
    const term = this.siteSearchTerm.toLowerCase();
    this.filteredSites = this.siteArray.filter(site => site.name.toLowerCase().includes(term));
  }

  selectSite(site: { id: number; name: string }): void {
    this.siteSearchTerm = site.name;
    this.editForm.get('siteId')?.setValue(site.id);
    this.showSitesDropdown = false;
  }

  hideSiteDropdown(): void {
    setTimeout(() => {
      this.showSitesDropdown = false;
    }, 200);
  }
}
