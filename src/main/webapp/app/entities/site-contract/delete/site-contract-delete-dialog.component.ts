import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISiteContract } from '../site-contract.model';
import { SiteContractService } from '../service/site-contract.service';

@Component({
  templateUrl: './site-contract-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SiteContractDeleteDialogComponent {
  siteContract?: ISiteContract;

  protected siteContractService = inject(SiteContractService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.siteContractService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
