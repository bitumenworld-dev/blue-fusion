import { Component, inject, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DataUtils } from 'app/core/util/data-util.service';
import { IAssetPlantPhoto } from '../asset-plant-photo.model';

@Component({
  selector: 'jhi-asset-plant-photo-detail',
  templateUrl: './asset-plant-photo-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class AssetPlantPhotoDetailComponent {
  assetPlantPhoto = input<IAssetPlantPhoto | null>(null);

  protected dataUtils = inject(DataUtils);

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
