package com.bitumen.bluefusion.service.assetPlantServiceReading.dto;

import com.bitumen.bluefusion.domain.enumeration.ServiceUnit;
import java.time.LocalDate;

public record AssetPlantServiceReadingRequest(
    Long assetPlantId,
    Float nextServiceSmrReading,
    Float estimatedUnitsPerDay,

    Float latestSmrReadings,
    Float serviceInterval,
    LocalDate lastServiceDate,
    LocalDate latestSmrDate,
    Float lastServiceSmr,
    ServiceUnit serviceUnit,
    LocalDate estimatedNextServiceDate
) {}
