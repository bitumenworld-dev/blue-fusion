import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { Storage } from '../storage.model';
import { StorageService } from '../service/storage.service';

@Component({
  templateUrl: './storage-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class StorageDeleteDialogComponent {
  storage?: Storage;

  protected storageService = inject(StorageService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.storageService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
