package com.bitumen.bluefusion.service.fuelTransaction.dto;

import com.bitumen.bluefusion.domain.enumeration.FuelType;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StorageUnitTransaction {

    private LocalDate transactionDate;
    private String fuelTransactionType;
    private FuelType fuelType;
    private String fleetNumber;
    private Float meterReadingStart;
    private Float meterReadingEnd;
    private Float litres;
    private String contractDivision;
    private Boolean isFillUp;
    private String fuelPump;
    private String note;
}
