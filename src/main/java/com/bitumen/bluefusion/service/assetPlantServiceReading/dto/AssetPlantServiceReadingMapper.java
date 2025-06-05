package com.bitumen.bluefusion.service.assetPlantServiceReading.dto;

import com.bitumen.bluefusion.domain.AssetPlantServiceReading;
import java.util.function.Function;

public interface AssetPlantServiceReadingMapper {
    Function<AssetPlantServiceReading, AssetPlantServiceReadingResponse> map = assetPlantServiceReading ->
        new AssetPlantServiceReadingResponse(
            assetPlantServiceReading.getAssetPlantServiceReadingId(),
            assetPlantServiceReading.getAssetPlant(),
            assetPlantServiceReading.getNextServiceSmrReading(),
            assetPlantServiceReading.getEstimatedUnitsPerDay(),
            assetPlantServiceReading.getEstimatedNextServiceDate(),
            assetPlantServiceReading.getLatestSmrReadings(),
            assetPlantServiceReading.getServiceInterval(),
            assetPlantServiceReading.getLastServiceDate(),
            assetPlantServiceReading.getLatestSmrDate(),
            assetPlantServiceReading.getLastServiceSmr(),
            assetPlantServiceReading.getServiceUnit()
        );
}
