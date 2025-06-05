package com.bitumen.bluefusion.service.assetPlantServiceReading.dto;

import com.bitumen.bluefusion.domain.AssetPlant;
import com.bitumen.bluefusion.domain.enumeration.ServiceUnit;
import java.time.LocalDate;

public record AssetPlantServiceReadingResponse(
    Long assetPlantServiceReadingId,
    AssetPlant assetPlantFleetNumber,
    Float nextServiceSmrReading,
    Float estimatedUnitsPerDay,
    LocalDate estimatedNextServiceDate,
    Float latestSmrReadings,
    Float serviceInterval,
    LocalDate lastServiceDate,
    LocalDate latestSmrDate,
    Float lastServiceSmr,
    ServiceUnit serviceUnit
) {}
