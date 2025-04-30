import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPlantCategory } from '../plant-category.model';
import { PlantCategoryService } from '../service/plant-category.service';

@Component({
  templateUrl: './plant-category-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PlantCategoryDeleteDialogComponent {
  plantCategory?: IPlantCategory;

  protected plantCategoryService = inject(PlantCategoryService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.plantCategoryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
