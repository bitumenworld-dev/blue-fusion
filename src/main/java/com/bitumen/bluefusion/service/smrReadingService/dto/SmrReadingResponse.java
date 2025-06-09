package com.bitumen.bluefusion.service.smrReadingService.dto;

import java.time.LocalDateTime;

public record SmrReadingResponse(
    Long smrReadingId,
    Long assetPlantId,
    Double smrReading,
    LocalDateTime readingDateTime,
    String unit,
    Long fuelTransactionHeaderId,
    String whatsappNumber
) {}
//TODO
//Add plantHours
