package com.bitumen.bluefusion.service.assetPlantServiceReading;

import com.bitumen.bluefusion.domain.AssetPlantServiceReading;
import com.bitumen.bluefusion.service.assetPlantServiceReading.dto.AssetPlantServiceReadingRequest;
import com.bitumen.bluefusion.service.assetPlantServiceReading.dto.AssetPlantServiceReadingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface AssetPlantServiceReadingService {
    AssetPlantServiceReadingResponse save(AssetPlantServiceReadingRequest assetPlantServiceReadingRequest);

    AssetPlantServiceReadingResponse update(
        Long assetPlantServiceReadingId,
        AssetPlantServiceReadingRequest assetPlantServiceReadingRequest
    );

    AssetPlantServiceReadingResponse partialUpdate(Long assetPlantServiceReadingId);

    AssetPlantServiceReadingResponse partialUpdate(
        Long assetPlantServiceReadingId,
        AssetPlantServiceReadingRequest assetPlantServiceReadingRequest
    );

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    Page<AssetPlantServiceReadingResponse> findAll(Pageable pageable, Long assetPlantId, Boolean isActive, String serviceUnit);

    @Transactional(readOnly = true)
    AssetPlantServiceReading findOne(Long assetPlantServiceReadingId);

    void delete(Long assetPlantServiceReadingId);
}
