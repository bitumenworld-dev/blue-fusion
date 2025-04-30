package com.bitumen.bluefusion.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FuelPumpTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FuelPump getFuelPumpSample1() {
        return new FuelPump().id(1L).fuelPumpId(1L).fuelPumpNumber("fuelPumpNumber1").currentStorageUnitId(1L);
    }

    public static FuelPump getFuelPumpSample2() {
        return new FuelPump().id(2L).fuelPumpId(2L).fuelPumpNumber("fuelPumpNumber2").currentStorageUnitId(2L);
    }

    public static FuelPump getFuelPumpRandomSampleGenerator() {
        return new FuelPump()
            .id(longCount.incrementAndGet())
            .fuelPumpId(longCount.incrementAndGet())
            .fuelPumpNumber(UUID.randomUUID().toString())
            .currentStorageUnitId(longCount.incrementAndGet());
    }
}
