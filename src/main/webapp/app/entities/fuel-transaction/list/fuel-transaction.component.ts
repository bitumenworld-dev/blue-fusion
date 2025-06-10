import { Component, inject, NgZone, OnInit, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Subscription } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { FuelTransaction, NewFuelTransaction } from '../fuel-transaction.model';
import {
  StorageUnitTransactionsResponseType,
  FuelTransactionArrayResponseType,
  FuelTransactionResponseType,
  FuelTransactionService,
} from '../service/fuel-transaction.service';
import { FuelType, FuelTypes } from '../../enumerations/fuel-type.model';
import { StorageUnitTransaction, StorageUnitTransactions, StorageUnitPump } from '../storage-unit.model';
import { CompanyEntityArrayResponseType, CompanyService } from '../../company/service/company.service';
import { ICompany } from '../../company/company.model';
import { Storage } from '../../storage/storage.model';
import { FuelTransactionType, FuelTransactionTypes } from '../../enumerations/transaction-type.model';
import {
  ContractDivisionEntityArrayResponseType,
  ContractDivisionService,
} from '../../contract-division/service/contract-division.service';
import { ContractDivision } from '../../contract-division/contract-division.model';
import { StorageEntityArrayResponseType, StorageService } from '../../storage/service/storage.service';
import { FuelPumpArrayResponseType, FuelPumpService } from '../../fuel-pump/service/fuel-pump.service';
import { FuelPump } from '../../fuel-pump/fuel-pump.model';
import { AssetPlantArrayResponseType, AssetPlantService } from '../../asset-plant/service/asset-plant.service';
import { AssetPlant } from '../../asset-plant/asset-plant.model';

@Component({
  templateUrl: './fuel-transaction.component.html',
  imports: [RouterModule, FormsModule, SharedModule, ReactiveFormsModule],
})
export class FuelTransactionComponent implements OnInit {
  subscription: Subscription | null = null;
  isLoading = false;
  isFillUp: boolean | null | undefined = false;
  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;
  selectedTransactionType = '';
  userFullName: string | null | undefined = '';
  smr: string = '';
  storageUnitPumps: StorageUnitPump[] | null = [];
  pumpStart: string = '';
  pumpEnd: string = '';
  receivedFuel: string = '';
  issuedFuel: string = '';
  unit: string | null | undefined = '';
  notes: string = '';
  transactionDate: Date | null | undefined = null;
  startDate: string | null = null;
  endDate: string | null = null;
  newFuelTransaction: NewFuelTransaction = {
    fuelTransactionId: null,
  };
  balance: string | null | undefined = '';

  //Fleet
  fleetArray: AssetPlant[] | null = [];
  fleetNumber: string | null | undefined = '';
  // @ts-ignore
  filteredFleet = [...this.fleetArray];
  showFleetDropdown = false;

  //Company variables
  companyId: number | null | undefined = 0;
  companiesArray: ICompany[] | null = [];
  companiesSearchTerm = '';
  // @ts-ignore
  filteredCompanies = [...this.companiesArray];
  showCompaniesDropdown = false;

  //Storage variables
  storageUnitArray: Storage[] | null = [];
  storageUnitSearchTerm: string | null | undefined = '';
  // @ts-ignore
  filteredStorageUnits = [...this.storageUnitArray];
  showStorageUnitDropdown = false;

  //Transfer Unit variables
  transferUnitArray: Storage[] | null = [];
  transferUnitSearchTerm: string | null | undefined = '';
  // @ts-ignore
  filteredTransferUnits = [...this.transferUnitArray];
  showTransferUnitDropdown = false;

  //Pump variable
  pumpsArray: FuelPump[] | null = [];
  pumpSearchTerm: string | null | undefined = '';
  // @ts-ignore
  filteredPumps = [...this.pumpsArray];
  showPumpDropdown = false;

  // Contract Division Search Dropdown
  contractDivisionArray: ContractDivision[] | null = [];
  divisionContract: string | null | undefined = '';
  // @ts-ignore
  filteredDivisionContracts = [...this.contractDivisionArray];
  showDivisionContractDropdown = false;

  fuelTypes: FuelType[] = FuelTypes;
  selectedFuelType = '';

  fuelTransactionTypes: FuelTransactionType[] = FuelTransactionTypes;

  showFleet = false;
  showUser = false;
  showSmr = false;
  showUnit = false;
  showNotes = false;
  showDivisionContract = false;
  showReceived = false;
  showIssued = false;
  showIsFillUp = false;
  showPumps = false;
  showTransferUnit = false;

  // fuelTransactionsArray
  storageUnitTransactions: StorageUnitTransactions | null | undefined = null;
  fuelTransactionsArray: StorageUnitTransaction[] | null | undefined = [];

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
  protected readonly fuelTransactionService = inject(FuelTransactionService);
  protected readonly fuelPumpService = inject(FuelPumpService);
  protected readonly storageService = inject(StorageService);
  protected readonly assetPlantService = inject(AssetPlantService);
  protected contractDivisionService = inject(ContractDivisionService);
  protected readonly companyService = inject(CompanyService);
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  ngOnInit(): void {
    this.companyService.query().subscribe({
      next: (res: CompanyEntityArrayResponseType) => {
        this.companiesArray = res.body;
      },
    });

    const startDate = new Date();
    startDate.setDate(startDate.getDate() - 30);
    this.startDate = startDate.toISOString().split('T')[0];

    const endDate = new Date();
    this.endDate = endDate.toISOString().split('T')[0];
  }

  // Company Filtering & Selection Logic
  filterCompanies(): void {
    const term = this.companiesSearchTerm.toLowerCase();
    // @ts-ignore
    this.filteredCompanies = this.companiesArray?.filter(company => company.name?.toLowerCase().includes(term));
  }

  selectCompany(company: { id: number; name: string }): void {
    this.companiesSearchTerm = company.name;
    this.showCompaniesDropdown = false;

    const queryObject: any = {
      companyId: company.id,
    };

    this.storageService.query(queryObject).subscribe({
      next: (res: StorageEntityArrayResponseType) => {
        this.storageUnitArray = res.body;
        this.transferUnitArray = res.body;
        this.filteredStorageUnits = [...this.storageUnitArray];
      },
    });

    this.companyId = company.id;
    this.pumpSearchTerm = '';
    this.storageUnitSearchTerm = '';
    this.newFuelTransaction.companyId = company.id;
  }

  hideCompanyDropdown(): void {
    setTimeout(() => {
      this.showCompaniesDropdown = false;
    }, 200);
  }

  filterPumps(): void {
    if (!this.pumpSearchTerm) {
      this.filteredPumps = [...this.pumpsArray];
      return;
    }
    const term = this.pumpSearchTerm.toLowerCase().trim();
    if (this.pumpsArray) {
      this.filteredPumps = this.pumpsArray.filter(pump => pump.fuelPumpCode?.toLowerCase().includes(term));
    }
  }

  filterTransferUnits(): void {
    if (!this.transferUnitSearchTerm) {
      this.filteredTransferUnits = [...this.transferUnitArray];
      return;
    }

    const term = this.transferUnitSearchTerm.toLowerCase().trim();
    if (this.transferUnitArray) {
      this.filteredTransferUnits = this.transferUnitArray.filter(transferUnit => transferUnit.name?.toLowerCase().includes(term));
    }
  }

  selectTransferUnit(transferUnit: Storage): void {
    this.transferUnitSearchTerm = transferUnit.name;
    this.showTransferUnitDropdown = false;
    this.newFuelTransaction.transferUnitId = transferUnit.id;
  }

  hideTransferUnitDropdown(): void {
    setTimeout(() => {
      this.showTransferUnitDropdown = false;
    }, 200);
  }

  filterStorageUnits(): void {
    if (!this.storageUnitSearchTerm) {
      this.filteredStorageUnits = [...this.storageUnitArray];
      return;
    }

    const term = this.storageUnitSearchTerm.toLowerCase().trim();
    if (this.storageUnitArray) {
      this.filteredStorageUnits = this.storageUnitArray.filter(storageUnit => storageUnit.name?.toLowerCase().includes(term));
    }
  }

  selectStorageUnit(storageUnit: Storage): void {
    this.storageUnitSearchTerm = storageUnit.name;
    this.showStorageUnitDropdown = false;
    this.newFuelTransaction.storageId = storageUnit.id;

    const queryObject: any = {
      storageId: storageUnit.id,
    };

    this.fuelPumpService.query(queryObject).subscribe({
      next: (response: FuelPumpArrayResponseType) => {
        this.pumpsArray = response.body;
        this.filteredPumps = [...this.pumpsArray];

        //Set Pump
        this.pumpSearchTerm = '';
        if (this.pumpsArray && this.pumpsArray.length > 0) {
          const pump: FuelPump = this.pumpsArray[0];
          this.selectPump(pump);
        }
      },
    });

    const content = storageUnit.storageContent?.toUpperCase();
    if (content === FuelType.PETROL || content === FuelType.DIESEL) {
      this.selectedFuelType = content;
      this.newFuelTransaction.fuelType = content;
    } else {
      this.selectedFuelType = '';
    }

    this.getTransactions(storageUnit.id);
  }

  hideStorageUnitDropdown(): void {
    setTimeout(() => {
      this.showStorageUnitDropdown = false;
    }, 200);
  }

  getTransactions(storageId: number): void {
    //fuelTransactionsArray
    const fuelTransactionsQueryObject: any = {
      storageUnitId: storageId,
      startDate: this.startDate,
      endDate: this.endDate,
    };
    this.fuelTransactionService.query(fuelTransactionsQueryObject).subscribe({
      next: (res: StorageUnitTransactionsResponseType) => {
        this.storageUnitTransactions = res.body;
        if (this.storageUnitTransactions) {
          if (this.storageUnitTransactions.latestTransactionDate) {
            this.transactionDate = this.storageUnitTransactions.latestTransactionDate;
          }
          this.balance = this.storageUnitTransactions.currentStorageBalance;
          this.fuelTransactionsArray = this.storageUnitTransactions.transactions;
          this.transactionDate = this.storageUnitTransactions.latestTransactionDate;
          // @ts-ignore
          this.storageUnitPumps = [...this.storageUnitTransactions.pumpReadings];
        }
      },
    });
  }

  selectPump(pump: FuelPump): void {
    this.pumpSearchTerm = pump.description;
    this.showPumpDropdown = false;
    this.newFuelTransaction.pumpId = pump.id;
  }

  hidePumpDropdown(): void {
    setTimeout(() => {
      this.showPumpDropdown = false;
    }, 200);
  }

  searchFleet(): void {
    if (!this.fleetNumber) {
      return;
    }

    if (this.fleetNumber.length > 3) {
      const queryObject: any = {
        company: this.companyId,
        fleetNumber: this.fleetNumber,
      };

      this.assetPlantService.query(queryObject).subscribe({
        next: (res: AssetPlantArrayResponseType) => {
          this.fleetArray = res.body;
          this.filteredFleet = [...this.fleetArray];
          this.showFleetDropdown = true;
        },
      });
    } else {
      //
    }
  }

  hideFleetDropdown(): void {
    setTimeout(() => {
      this.showFleetDropdown = false;
    }, 200);
  }

  selectFleet(fleet: AssetPlant): void {
    this.fleetNumber = fleet.fleetNumber;
    this.hideFleetDropdown();
    this.userFullName = fleet.currentOperator;
    this.unit = fleet.smrReaderType;
    this.newFuelTransaction.assetPlantId = fleet.assetPlantId;
  }

  selectTransactionType(transactionType: FuelTransactionType): void {
    this.resetFields();
    this.selectedTransactionType = transactionType;
    if (transactionType === FuelTransactionType.FLEET_ISSUANCE) {
      this.showIssuanceFields();
    }
    if (transactionType === FuelTransactionType.THIRD_PARTY_ISSUANCE) {
      this.showIssuanceFields();
    }
    if (transactionType === FuelTransactionType.GRV) {
      this.showReceived = true;
      this.showNotes = true;
    }
    if (transactionType === FuelTransactionType.TRANSFER) {
      this.showTransferFields();
    }

    this.newFuelTransaction.transactionType = transactionType;
  }

  searchDivisionContract(): void {
    if (!this.divisionContract) {
      return;
    }

    if (this.divisionContract.length > 3) {
      const queryObject: any = {
        contractDivisionNumber: this.divisionContract,
      };

      this.contractDivisionService.query(queryObject).subscribe({
        next: (res: ContractDivisionEntityArrayResponseType) => {
          this.contractDivisionArray = res.body;
          this.filteredDivisionContracts = [...this.contractDivisionArray];
          this.showDivisionContractDropdown = true;
        },
      });
    } else {
      //
    }
  }

  selectDivisionContract(contractDivision: ContractDivision): void {
    this.divisionContract = contractDivision.contractDivisionNumber;
    this.showDivisionContractDropdown = false;
    this.newFuelTransaction.contractDivisionId = contractDivision.contractDivisionId;
  }

  hideDivisionContractDropdown(): void {
    setTimeout(() => {
      this.showDivisionContractDropdown = false;
    }, 200);
  }

  showTransferFields(): void {
    this.showTransferUnit = true;
    this.showNotes = true;
    this.showIssued = true;
    this.showPumps = true;
  }

  showIssuanceFields(): void {
    this.showFleet = true;
    this.showUser = true;
    this.showSmr = true;
    this.showUnit = true;
    this.showNotes = true;
    this.showDivisionContract = true;
    this.showIssued = true;
    this.showIsFillUp = true;
    this.showPumps = true;
  }

  resetFields(): void {
    this.showFleet = false;
    this.showUser = false;
    this.showSmr = false;
    this.showUnit = false;
    this.showNotes = false;
    this.showDivisionContract = false;
    this.showReceived = false;
    this.showIssued = false;
    this.showIsFillUp = false;
    this.showPumps = false;
    this.showTransferUnit = false;
  }

  saveFuelTransaction(): void {
    this.newFuelTransaction.smr = this.smr;
    this.newFuelTransaction.transactionDate = this.transactionDate;
    this.newFuelTransaction.note = this.notes;
    this.newFuelTransaction.isFillUp = this.isFillUp;
    this.newFuelTransaction.meterReadingStart = this.pumpStart;
    this.newFuelTransaction.meterReadingEnd = this.pumpEnd;
    this.newFuelTransaction.litres = this.issuedFuel;

    this.fuelTransactionService.create(this.newFuelTransaction).subscribe({
      next: (res: FuelTransactionResponseType) => {},
    });

    // @ts-ignore
    this.getTransactions(this.newFuelTransaction.storageId);
    this.newFuelTransactionLineReset();
  }
  populateNote(): void {
    this.notes = this.issuedFuel + 'L  ' + this.newFuelTransaction.transactionType + ' TO ' + this.fleetNumber;
  }

  newFuelTransactionLineReset(): void {
    this.newFuelTransaction.smr = '';
    this.newFuelTransaction.note = '';
    this.newFuelTransaction.isFillUp = false;
    this.newFuelTransaction.meterReadingStart = '';
    this.newFuelTransaction.meterReadingEnd = '';
    this.newFuelTransaction.litres = '';
    this.newFuelTransaction.assetPlantId = null;
    this.newFuelTransaction.contractDivisionId = null;
    this.newFuelTransaction.transferUnitId = null;

    this.smr = '';
    this.unit = '';
    this.notes = '';
    this.isFillUp = false;
    this.pumpStart = '';
    this.pumpEnd = '';
    this.issuedFuel = '';
    this.fleetNumber = '';
    this.divisionContract = '';
  }
}
