import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IManufacturerModel } from '../manufacturer-model.model';

@Component({
  selector: 'jhi-manufacturer-model-detail',
  templateUrl: './manufacturer-model-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class ManufacturerModelDetailComponent {
  manufacturerModel = input<IManufacturerModel | null>(null);

  previousState(): void {
    window.history.back();
  }
}
