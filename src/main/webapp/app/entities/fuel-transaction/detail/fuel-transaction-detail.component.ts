import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FuelTransaction } from '../fuel-transaction.model';

@Component({
  templateUrl: './fuel-transaction-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class FuelTransactionDetailComponent {
  fuelTransaction = input<FuelTransaction | null>(null);

  previousState(): void {
    window.history.back();
  }
}
