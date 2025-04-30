import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IFuelPump } from '../fuel-pump.model';

@Component({
  selector: 'jhi-fuel-pump-detail',
  templateUrl: './fuel-pump-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class FuelPumpDetailComponent {
  fuelPump = input<IFuelPump | null>(null);

  previousState(): void {
    window.history.back();
  }
}
