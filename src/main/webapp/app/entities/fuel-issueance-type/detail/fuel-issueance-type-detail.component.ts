import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IFuelIssueanceType } from '../fuel-issueance-type.model';

@Component({
  selector: 'jhi-fuel-issueance-type-detail',
  templateUrl: './fuel-issueance-type-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class FuelIssueanceTypeDetailComponent {
  fuelIssueanceType = input<IFuelIssueanceType | null>(null);

  previousState(): void {
    window.history.back();
  }
}
