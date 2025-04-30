import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAssetPlantServiceReading } from '../asset-plant-service-reading.model';
import { AssetPlantServiceReadingService } from '../service/asset-plant-service-reading.service';

@Component({
  templateUrl: './asset-plant-service-reading-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AssetPlantServiceReadingDeleteDialogComponent {
  assetPlantServiceReading?: IAssetPlantServiceReading;

  protected assetPlantServiceReadingService = inject(AssetPlantServiceReadingService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.assetPlantServiceReadingService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
