package com.bitumen.bluefusion.service.smrReadingService.dto;

import com.bitumen.bluefusion.domain.SmrReading;
import java.util.function.Function;

public interface SmrReadingMapper {
    Function<SmrReading, SmrReadingResponse> map = smrReading ->
        new SmrReadingResponse(
            smrReading.getSmrReadingId(),
            smrReading.getAssetPlant() != null ? smrReading.getAssetPlant().getAssetPlantId() : null,
            smrReading.getSmrReading() != null ? smrReading.getSmrReading().doubleValue() : null,
            smrReading.getReadingDateTime(),
            smrReading.getUnit(),
            smrReading.getFuelTransactionHeaderId() != null ? Long.valueOf(smrReading.getFuelTransactionHeaderId()) : null,
            smrReading.getWhatsappNumber()
        );
}
