package com.bitumen.bluefusion.service.assetPlantServiceReading;

import com.bitumen.bluefusion.domain.AssetPlantServiceReading;
import com.bitumen.bluefusion.service.assetPlantServiceReading.dto.AssetPlantServiceReadingMapper;
import com.bitumen.bluefusion.service.assetPlantServiceReading.dto.AssetPlantServiceReadingRequest;
import com.bitumen.bluefusion.service.assetPlantServiceReading.dto.AssetPlantServiceReadingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssetPlantServiceReadingService {
    AssetPlantServiceReadingResponse save(AssetPlantServiceReadingRequest assetPlantServiceReadingRequest);

    AssetPlantServiceReadingResponse update(
        Long assetPlantServiceReadingId,
        AssetPlantServiceReadingRequest assetPlantServiceReadingRequest
    );

    AssetPlantServiceReadingResponse partialUpdate(
        Long assetPlantServiceReadingId,
        AssetPlantServiceReadingRequest assetPlantServiceReadingRequest
    );

    Page<AssetPlantServiceReadingResponse> findAll(Pageable pageable, Long assetPlant, Boolean isActive, String serviceUnit);

    AssetPlantServiceReadingResponse findOne(Long assetPlantServiceReadingId);

    void delete(Long assetPlantServiceReadingId);
}
