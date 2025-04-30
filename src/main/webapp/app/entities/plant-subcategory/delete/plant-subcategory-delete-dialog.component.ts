import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPlantSubcategory } from '../plant-subcategory.model';
import { PlantSubcategoryService } from '../service/plant-subcategory.service';

@Component({
  templateUrl: './plant-subcategory-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PlantSubcategoryDeleteDialogComponent {
  plantSubcategory?: IPlantSubcategory;

  protected plantSubcategoryService = inject(PlantSubcategoryService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.plantSubcategoryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
