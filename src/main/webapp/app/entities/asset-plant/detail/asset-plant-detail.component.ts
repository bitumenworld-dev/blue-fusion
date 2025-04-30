import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IAssetPlant } from '../asset-plant.model';

@Component({
  selector: 'jhi-asset-plant-detail',
  templateUrl: './asset-plant-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class AssetPlantDetailComponent {
  assetPlant = input<IAssetPlant | null>(null);

  previousState(): void {
    window.history.back();
  }
}
