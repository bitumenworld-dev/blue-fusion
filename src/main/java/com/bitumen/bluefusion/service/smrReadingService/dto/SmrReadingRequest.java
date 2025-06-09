package com.bitumen.bluefusion.service.smrReadingService.dto;

import java.time.LocalDateTime;

public record SmrReadingRequest(
    Long assetPlantId,
    Double smrReading,
    LocalDateTime readingDateTime,
    String unit,
    Long fuelTransactionHeaderId,
    String whatsappNumber
) {}
