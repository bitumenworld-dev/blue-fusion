import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFuelIssueanceType } from '../fuel-issueance-type.model';
import { FuelIssueanceTypeService } from '../service/fuel-issueance-type.service';

@Component({
  templateUrl: './fuel-issueance-type-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FuelIssueanceTypeDeleteDialogComponent {
  fuelIssueanceType?: IFuelIssueanceType;

  protected fuelIssueanceTypeService = inject(FuelIssueanceTypeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fuelIssueanceTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
