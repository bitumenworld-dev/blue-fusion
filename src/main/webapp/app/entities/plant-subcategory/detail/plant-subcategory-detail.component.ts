import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IPlantSubcategory } from '../plant-subcategory.model';

@Component({
  selector: 'jhi-plant-subcategory-detail',
  templateUrl: './plant-subcategory-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class PlantSubcategoryDetailComponent {
  plantSubcategory = input<IPlantSubcategory | null>(null);

  previousState(): void {
    window.history.back();
  }
}
