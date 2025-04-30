import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IContractDivision } from '../contract-division.model';

@Component({
  selector: 'jhi-contract-division-detail',
  templateUrl: './contract-division-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class ContractDivisionDetailComponent {
  contractDivision = input<IContractDivision | null>(null);

  previousState(): void {
    window.history.back();
  }
}
