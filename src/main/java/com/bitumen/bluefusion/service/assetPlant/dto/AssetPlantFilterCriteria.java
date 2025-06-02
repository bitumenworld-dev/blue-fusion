package com.bitumen.bluefusion.service.assetPlant.dto;

public record AssetPlantFilterCriteria(
    String fleetNumber,
    Long companyId,
    Long makeId,
    Long modelId,
    Long categoryId,
    Long subcategoryId,
    Boolean trackConsumption,
    Boolean trackSmrReading
) {}
