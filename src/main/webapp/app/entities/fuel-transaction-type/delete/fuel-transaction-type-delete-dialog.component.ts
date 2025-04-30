import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFuelTransactionType } from '../fuel-transaction-type.model';
import { FuelTransactionTypeService } from '../service/fuel-transaction-type.service';

@Component({
  templateUrl: './fuel-transaction-type-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FuelTransactionTypeDeleteDialogComponent {
  fuelTransactionType?: IFuelTransactionType;

  protected fuelTransactionTypeService = inject(FuelTransactionTypeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fuelTransactionTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
