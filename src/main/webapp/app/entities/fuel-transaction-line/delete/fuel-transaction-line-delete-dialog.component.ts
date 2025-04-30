import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFuelTransactionLine } from '../fuel-transaction-line.model';
import { FuelTransactionLineService } from '../service/fuel-transaction-line.service';

@Component({
  templateUrl: './fuel-transaction-line-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FuelTransactionLineDeleteDialogComponent {
  fuelTransactionLine?: IFuelTransactionLine;

  protected fuelTransactionLineService = inject(FuelTransactionLineService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fuelTransactionLineService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
