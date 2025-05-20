import { Component, inject, NgZone, OnInit, signal } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { combineLatest, filter, Observable, Subscription, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { FuelTransaction } from '../fuel-transaction.model';
import { FuelTransactionFormGroup, FuelTransactionFormService } from './fuel-transaction-form.service';
import { EntityArrayResponseType, FuelTransactionService } from '../service/fuel-transaction.service';
// import {
//   FuelTransactionHeaderDeleteDialogComponent
// } from '../delete/fuel-transaction-header-delete-dialog.component';
import { CompanyEntityArrayResponseType, CompanyService } from '../../company/service/company.service';
import { ICompany } from '../../company/company.model';
import { Storage } from '../../storage/storage.model';
import { StorageService, StorageEntityArrayResponseType } from '../../storage/service/storage.service';

@Component({
  templateUrl: './fuel-transaction.component.html',
  imports: [RouterModule, FormsModule, SharedModule, ReactiveFormsModule],
})
export class FuelTransactionComponent implements OnInit {
  subscription: Subscription | null = null;
  fuelTransactions = signal<FuelTransaction[]>([]);
  isLoading = false;

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;
  selectedFuelType = '';
  selectedTransactionType = '';
  userFullName = '';
  smr = '';
  fleetNumber = '';
  receivedFuel = '';
  issuedFuel = '';
  unit = '';
  divisionContract = '';
  notes = '';
  date: Date | null = null;
  startDate: Date | null = null;
  endDate: Date | null = null;
  balance = 15000;
  pump1 = 1233123;
  pump2 = 123123123;

  companiesArray: ICompany[] | null = [];

  companiesSearchTerm = '';
  // @ts-ignore
  filteredCompanies = [...this.companiesArray];
  showCompaniesDropdown = false;

  storageUnitArray: Storage[] | null = [];
  storageUnitSearchTerm = '';
  // @ts-ignore
  filteredStorageUnits = [...this.storageUnitArray];
  showStorageUnitDropdown = false;

  pumpsArray = [
    { id: 1, name: 'PUMP01' },
    { id: 2, name: 'PUMP02' },
    { id: 3, name: 'ALL' },
  ];
  pump = '';
  showPumpDropdown = false;

  // consumptionsArray dummy
  fuelTransactionsArray = [
    {
      id: '1',
      date: '2025-05-01',
      fleetNumber: 'INV-20256475-09',
      user: 'Lionel Samvura',
      smr: '1200',
      unit: 'KILOMETER_BASED',
      notes: 'issue  to third party',
      divionContract: 'CB0002',
      received: '40',
      issued: '23',
      balance: '12000',
      pump: 'Pump01',
      isFillUp: 'true',
    },
    {
      id: '1',
      date: '2025-05-02',
      fleetNumber: 'INV-20256475-09',
      user: 'Lionel Samvura',
      smr: '1200',
      unit: 'KILOMETER_BASED',
      notes: 'issue  to third party',
      divionContract: 'CB0002',
      received: '40',
      issued: '23',
      balance: '12000',
      pump: 'Pump01',
      isFillUp: 'true',
    },
    {
      id: '1',
      date: '2025-05-01',
      fleetNumber: 'INV-20256475-09',
      user: 'Lionel Samvura',
      smr: '1200',
      unit: 'KILOMETER_BASED',
      notes: 'issue  to third party',
      divionContract: 'CB0002',
      received: '40',
      issued: '23',
      balance: '12000',
      pump: 'Pump01',
      isFillUp: 'true',
    },
    {
      id: '1',
      date: '2025-05-03',
      fleetNumber: 'INV-20256475-09',
      user: 'Lionel Samvura',
      smr: '1200',
      unit: 'KILOMETER_BASED',
      notes: 'issue  to third party',
      divionContract: 'CB0002',
      received: '40',
      issued: '23',
      balance: '12000',
      pump: 'Pump01',
      isFillUp: 'true',
    },
    {
      id: '1',
      date: '2025-05-04',
      fleetNumber: 'INV-20256475-09',
      user: 'Lionel Samvura',
      smr: '1200',
      unit: 'KILOMETER_BASED',
      notes: 'issue  to third party',
      divionContract: 'CB0002',
      received: '40',
      issued: '23',
      balance: '12000',
      pump: 'Pump01',
      isFillUp: 'true',
    },
    {
      id: '1',
      date: '2025-05-05',
      fleetNumber: 'INV-20256475-09',
      user: 'Lionel Samvura',
      smr: '1200',
      unit: 'KILOMETER_BASED',
      notes: 'issue  to third party',
      divionContract: 'CB0002',
      received: '40',
      issued: '23',
      balance: '12000',
      pump: 'Pump01',
      isFillUp: 'true',
    },
    {
      id: '1',
      date: '2025-05-06',
      fleetNumber: 'INV-20256475-09',
      user: 'Lionel Samvura',
      smr: '1200',
      unit: 'KILOMETER_BASED',
      notes: 'issue  to third party',
      divionContract: 'CB0002',
      received: '40',
      issued: '23',
      balance: '12000',
      pump: 'Pump01',
      isFillUp: 'true',
    },
  ];

  // consumptionsArray dummy
  consumptionsArray = [
    { date: '2025-05-01', document: 'INV-20256475-09', consumption: '54.6 L' },
    { date: '2025-05-01', document: 'INV-20256475-09', consumption: '54.6 L' },
    { date: '2025-05-01', document: 'INV-20256475-09', consumption: '54.6 L' },
    { date: '2025-05-01', document: 'INV-20256475-09', consumption: '54.6 L' },
    { date: '2025-05-01', document: 'INV-20256475-09', consumption: '54.6 L' },
    { date: '2025-05-01', document: 'INV-20256475-09', consumption: '54.6 L' },
    { date: '2025-05-01', document: 'INV-20256475-09', consumption: '54.6 L' },
    { date: '2025-05-01', document: 'INV-20256475-09', consumption: '54.6 L' },
    { date: '2025-05-01', document: 'INV-20256475-09', consumption: '54.6 L' },
  ];

  // consumptionsArray dummy
  smrReadingsArray = [
    { date: '2025-05-01', issueNo: 'INV-20256475-09', litres: '54.6 L', reading: '10034' },
    { date: '2025-05-01', issueNo: 'INV-20256475-09', litres: '54.6 L', reading: '10034' },
    { date: '2025-05-01', issueNo: 'INV-20256475-09', litres: '54.6 L', reading: '10034' },
    { date: '2025-05-01', issueNo: 'INV-20256475-09', litres: '54.6 L', reading: '10034' },
    { date: '2025-05-01', issueNo: 'INV-20256475-09', litres: '54.6 L', reading: '10034' },
    { date: '2025-05-01', issueNo: 'INV-20256475-09', litres: '54.6 L', reading: '10034' },
    { date: '2025-05-01', issueNo: 'INV-20256475-09', litres: '54.6 L', reading: '10034' },
    { date: '2025-05-01', issueNo: 'INV-20256475-09', litres: '54.6 L', reading: '10034' },
    { date: '2025-05-01', issueNo: 'INV-20256475-09', litres: '54.6 L', reading: '10034' },
  ];

  public readonly router = inject(Router);
  protected readonly fuelTransactionHeaderService = inject(FuelTransactionService);
  protected readonly storageService = inject(StorageService);
  protected fuelTransactionFormService = inject(FuelTransactionFormService);
  protected readonly companyService = inject(CompanyService);
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (item: FuelTransaction): number => this.fuelTransactionHeaderService.getFuelTransactionHeaderIdentifier(item);
  editForm: FuelTransactionFormGroup = this.fuelTransactionFormService.createFuelTransactionFormGroup();

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => this.load()),
      )
      .subscribe();
  }

  delete(fuelTransactionHeader: FuelTransaction): void {
    // const modalRef = this.modalService.open(FuelTransactionHeaderDeleteDialogComponent, {
    //   size: 'lg',
    //   backdrop: 'static'
    // });
    // modalRef.componentInstance.fuelTransactionHeader = fuelTransactionHeader;
    // // unsubscribe not needed because closed completes on modal close
    // modalRef.closed
    //   .pipe(
    //     filter(reason => reason === ITEM_DELETED_EVENT),
    //     tap(() => this.load())
    //   )
    //   .subscribe();
  }

  load(): void {
    this.queryBackend().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToPage(page: number): void {
    this.handleNavigation(page);
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.fuelTransactions.set(dataFromBody);
  }

  protected fillComponentAttributesFromResponseBody(data: FuelTransaction[] | null): FuelTransaction[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    const { page } = this;

    this.isLoading = true;
    const pageToLoad: number = page;
    const queryObject: any = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
    };

    this.companyService.query().subscribe({
      next: (res: CompanyEntityArrayResponseType) => {
        this.companiesArray = res.body;
      },
    });

    return this.fuelTransactionHeaderService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(page: number): void {
    const queryParamsObj = {
      page,
      size: this.itemsPerPage,
    };

    this.ngZone.run(() => {
      this.router.navigate(['./'], {
        relativeTo: this.activatedRoute,
        queryParams: queryParamsObj,
      });
    });
  }

  // Company Filtering & Selection Logic
  filterCompanies(): void {
    const term = this.companiesSearchTerm.toLowerCase();
    // @ts-ignore
    this.filteredCompanies = this.companiesArray?.filter(company => company.name?.toLowerCase().includes(term));
  }

  selectCompany(company: { id: number; name: string }): void {
    this.companiesSearchTerm = company.name;
    this.editForm.get('companyId')?.setValue(company.id);
    this.showCompaniesDropdown = false;

    const queryObject: any = {
      companyId: company.id,
    };

    this.storageService.query(queryObject).subscribe({
      next: (res: StorageEntityArrayResponseType) => {
        this.storageUnitArray = res.body;
      },
    });
  }

  hideCompanyDropdown(): void {
    setTimeout(() => {
      this.showCompaniesDropdown = false;
    }, 200);
  }

  // Storage Filtering & Selection Logic
  filterStorageUnits(): void {
    const term = this.storageUnitSearchTerm.toLowerCase();
    // @ts-ignore
    this.filteredStorageUnits = this.storageUnitArray.filter(storageUnit => storageUnit.name.toLowerCase().includes(term));
  }

  selectStorageUnit(storageUnit: { id: number; name: string }): void {
    this.storageUnitSearchTerm = storageUnit.name;
    this.editForm.get('storageUnitId')?.setValue(storageUnit.id);
    this.showStorageUnitDropdown = false;
  }

  hideStorageUnitDropdown(): void {
    setTimeout(() => {
      this.showStorageUnitDropdown = false;
    }, 200);
  }

  selectPump(pump: { id: number; name: string }): void {
    this.pump = pump.name;
    // this.fuelTransactionForm.get('companyId')?.setValue(company.id);
    this.showPumpDropdown = false;
  }

  hidePumpDropdown(): void {
    setTimeout(() => {
      this.showPumpDropdown = false;
    }, 200);
  }
}
