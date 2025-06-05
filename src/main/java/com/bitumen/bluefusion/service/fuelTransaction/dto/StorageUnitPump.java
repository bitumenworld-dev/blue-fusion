package com.bitumen.bluefusion.service.fuelTransaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StorageUnitPump {

    private String fuelPump;
    private Float meterReading;
}
