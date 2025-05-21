package com.bitumen.bluefusion.service.fuelPump.dto;

import java.time.LocalDate;

public record FuelPumpRequest(String fuelPumpCode, String description, Long storageId, Boolean isActive, LocalDate validFrom) {}
