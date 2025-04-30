import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ISiteContract } from '../site-contract.model';

@Component({
  selector: 'jhi-site-contract-detail',
  templateUrl: './site-contract-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class SiteContractDetailComponent {
  siteContract = input<ISiteContract | null>(null);

  previousState(): void {
    window.history.back();
  }
}
