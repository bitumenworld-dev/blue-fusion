import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatePipe } from 'app/shared/date';
import { IAssetPlantServiceReading } from '../asset-plant-service-reading.model';

@Component({
  selector: 'jhi-asset-plant-service-reading-detail',
  templateUrl: './asset-plant-service-reading-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatePipe],
})
export class AssetPlantServiceReadingDetailComponent {
  assetPlantServiceReading = input<IAssetPlantServiceReading | null>(null);

  previousState(): void {
    window.history.back();
  }
}
