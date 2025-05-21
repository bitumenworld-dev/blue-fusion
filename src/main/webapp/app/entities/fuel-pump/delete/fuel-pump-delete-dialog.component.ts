import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { FuelPump } from '../fuel-pump.model';
import { FuelPumpService } from '../service/fuel-pump.service';

@Component({
  templateUrl: './fuel-pump-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FuelPumpDeleteDialogComponent {
  fuelPump?: FuelPump;

  protected fuelPumpService = inject(FuelPumpService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fuelPumpService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
