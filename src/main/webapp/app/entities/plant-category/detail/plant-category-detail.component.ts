import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IPlantCategory } from '../plant-category.model';

@Component({
  selector: 'jhi-plant-category-detail',
  templateUrl: './plant-category-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class PlantCategoryDetailComponent {
  plantCategory = input<IPlantCategory | null>(null);

  previousState(): void {
    window.history.back();
  }
}
