import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IWorkshop } from '../workshop.model';
import { WorkshopService } from '../service/workshop.service';

@Component({
  templateUrl: './workshop-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class WorkshopDeleteDialogComponent {
  workshop?: IWorkshop;

  protected workshopService = inject(WorkshopService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workshopService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
