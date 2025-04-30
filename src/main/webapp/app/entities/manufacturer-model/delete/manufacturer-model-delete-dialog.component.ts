import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IManufacturerModel } from '../manufacturer-model.model';
import { ManufacturerModelService } from '../service/manufacturer-model.service';

@Component({
  templateUrl: './manufacturer-model-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ManufacturerModelDeleteDialogComponent {
  manufacturerModel?: IManufacturerModel;

  protected manufacturerModelService = inject(ManufacturerModelService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.manufacturerModelService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
