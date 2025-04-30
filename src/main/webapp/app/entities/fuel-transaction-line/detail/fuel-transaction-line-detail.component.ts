import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IFuelTransactionLine } from '../fuel-transaction-line.model';

@Component({
  selector: 'jhi-fuel-transaction-line-detail',
  templateUrl: './fuel-transaction-line-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class FuelTransactionLineDetailComponent {
  fuelTransactionLine = input<IFuelTransactionLine | null>(null);

  previousState(): void {
    window.history.back();
  }
}
