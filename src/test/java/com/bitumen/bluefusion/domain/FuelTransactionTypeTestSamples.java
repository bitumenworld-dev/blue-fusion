package com.bitumen.bluefusion.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FuelTransactionTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FuelTransactionType getFuelTransactionTypeSample1() {
        return new FuelTransactionType().id(1L).fuelTransactionTypeId(1L).fuelTransactionType("fuelTransactionType1");
    }

    public static FuelTransactionType getFuelTransactionTypeSample2() {
        return new FuelTransactionType().id(2L).fuelTransactionTypeId(2L).fuelTransactionType("fuelTransactionType2");
    }

    public static FuelTransactionType getFuelTransactionTypeRandomSampleGenerator() {
        return new FuelTransactionType()
            .id(longCount.incrementAndGet())
            .fuelTransactionTypeId(longCount.incrementAndGet())
            .fuelTransactionType(UUID.randomUUID().toString());
    }
}
