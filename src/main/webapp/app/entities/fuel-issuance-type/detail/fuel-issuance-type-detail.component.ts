import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IFuelIssuanceType } from '../fuel-issuance-type.model';

@Component({
  selector: 'jhi-fuel-issuance-type-detail',
  templateUrl: './fuel-issuance-type-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class FuelIssuanceTypeDetailComponent {
  fuelIssuanceType = input<IFuelIssuanceType | null>(null);

  previousState(): void {
    window.history.back();
  }
}
