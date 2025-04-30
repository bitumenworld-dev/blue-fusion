import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IFuelTransactionHeader } from '../fuel-transaction-header.model';

@Component({
  selector: 'jhi-fuel-transaction-header-detail',
  templateUrl: './fuel-transaction-header-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class FuelTransactionHeaderDetailComponent {
  fuelTransactionHeader = input<IFuelTransactionHeader | null>(null);

  previousState(): void {
    window.history.back();
  }
}
