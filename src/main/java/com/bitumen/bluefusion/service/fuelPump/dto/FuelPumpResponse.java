package com.bitumen.bluefusion.service.fuelPump.dto;

import java.time.LocalDate;

public record FuelPumpResponse(
    Long id,
    String fuelPumpCode,
    String description,
    Long storageId,
    String storage,
    Boolean isActive,
    LocalDate validFrom
) {}
