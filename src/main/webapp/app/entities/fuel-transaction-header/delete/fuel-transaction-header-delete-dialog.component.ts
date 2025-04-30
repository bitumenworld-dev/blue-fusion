import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFuelTransactionHeader } from '../fuel-transaction-header.model';
import { FuelTransactionHeaderService } from '../service/fuel-transaction-header.service';

@Component({
  templateUrl: './fuel-transaction-header-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FuelTransactionHeaderDeleteDialogComponent {
  fuelTransactionHeader?: IFuelTransactionHeader;

  protected fuelTransactionHeaderService = inject(FuelTransactionHeaderService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fuelTransactionHeaderService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
