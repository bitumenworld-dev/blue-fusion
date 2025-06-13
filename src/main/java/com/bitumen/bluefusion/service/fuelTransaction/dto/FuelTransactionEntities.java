package com.bitumen.bluefusion.service.fuelTransaction.dto;

import com.bitumen.bluefusion.domain.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class FuelTransactionEntities {

    private final Company company;
    private final AssetPlant assetPlant;
    private final Storage storageUnit;
    private final Storage transferUnit;
    private final FuelPump fuelPump;
    private final ContractDivision contractDivision;
    private final ThirdParty thirdParty;
    private final Site workshop;
}
