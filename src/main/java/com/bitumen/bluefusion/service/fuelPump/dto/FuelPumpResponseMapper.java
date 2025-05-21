package com.bitumen.bluefusion.service.fuelPump.dto;

import com.bitumen.bluefusion.domain.FuelPump;
import java.util.Objects;
import java.util.function.Function;

public interface FuelPumpResponseMapper {
    Function<FuelPump, FuelPumpResponse> map = fuelPump ->
        new FuelPumpResponse(
            fuelPump.getFuelPumpId(),
            fuelPump.getFuelPumpCode(),
            fuelPump.getDescription(),
            Objects.isNull(fuelPump.getCurrentStorageUnit()) ? null : fuelPump.getCurrentStorageUnit().getStorageId(),
            Objects.isNull(fuelPump.getCurrentStorageUnit()) ? null : fuelPump.getCurrentStorageUnit().getName(),
            fuelPump.getIsActive(),
            fuelPump.getValidFrom()
        );
}
