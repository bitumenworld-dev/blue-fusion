package com.bitumen.bluefusion.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FuelTransactionLineTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static FuelTransactionLine getFuelTransactionLineSample1() {
        return new FuelTransactionLine()
            .id(1L)
            .fuelTransactionLineId(1L)
            .fuelTransactionHeaderId(1L)
            .assetPlantId(1L)
            .contractDivisionId(1L)
            .issuanceTypeId(1L)
            .pumpId(1L)
            .storageUnitId(1L)
            .multiplier(1);
    }

    public static FuelTransactionLine getFuelTransactionLineSample2() {
        return new FuelTransactionLine()
            .id(2L)
            .fuelTransactionLineId(2L)
            .fuelTransactionHeaderId(2L)
            .assetPlantId(2L)
            .contractDivisionId(2L)
            .issuanceTypeId(2L)
            .pumpId(2L)
            .storageUnitId(2L)
            .multiplier(2);
    }

    public static FuelTransactionLine getFuelTransactionLineRandomSampleGenerator() {
        return new FuelTransactionLine()
            .id(longCount.incrementAndGet())
            .fuelTransactionLineId(longCount.incrementAndGet())
            .fuelTransactionHeaderId(longCount.incrementAndGet())
            .assetPlantId(longCount.incrementAndGet())
            .contractDivisionId(longCount.incrementAndGet())
            .issuanceTypeId(longCount.incrementAndGet())
            .pumpId(longCount.incrementAndGet())
            .storageUnitId(longCount.incrementAndGet())
            .multiplier(intCount.incrementAndGet());
    }
}
