import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IFuelTransactionType } from '../fuel-transaction-type.model';

@Component({
  selector: 'jhi-fuel-transaction-type-detail',
  templateUrl: './fuel-transaction-type-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class FuelTransactionTypeDetailComponent {
  fuelTransactionType = input<IFuelTransactionType | null>(null);

  previousState(): void {
    window.history.back();
  }
}
