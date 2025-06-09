package com.bitumen.bluefusion.service.smrReadingService;

import com.bitumen.bluefusion.service.smrReadingService.dto.SmrReadingRequest;
import com.bitumen.bluefusion.service.smrReadingService.dto.SmrReadingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface SmrReadingService {
    SmrReadingResponse save(SmrReadingRequest smrReadingRequest);

    SmrReadingResponse update(Long smrReadingId, SmrReadingRequest smrReadingRequest);

    SmrReadingResponse partialUpdate(Long smrReadingId, SmrReadingRequest smrReadingRequest);

    @Transactional(readOnly = true)
    Page<SmrReadingResponse> findAll(Pageable pageable, Long assetPlantId, String unit, Float smrReading);

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    SmrReadingResponse findOne(Long smrReadingId);

    void delete(Long smrReadingId);
}
