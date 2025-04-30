import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAssetPlant } from '../asset-plant.model';
import { AssetPlantService } from '../service/asset-plant.service';

@Component({
  templateUrl: './asset-plant-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AssetPlantDeleteDialogComponent {
  assetPlant?: IAssetPlant;

  protected assetPlantService = inject(AssetPlantService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.assetPlantService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
