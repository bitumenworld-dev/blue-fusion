package com.bitumen.bluefusion.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ManufacturerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Manufacturer getManufacturerSample1() {
        return new Manufacturer().id(1L).manufacturerId(1L).manufacturerName("manufacturerName1");
    }

    public static Manufacturer getManufacturerSample2() {
        return new Manufacturer().id(2L).manufacturerId(2L).manufacturerName("manufacturerName2");
    }

    public static Manufacturer getManufacturerRandomSampleGenerator() {
        return new Manufacturer()
            .id(longCount.incrementAndGet())
            .manufacturerId(longCount.incrementAndGet())
            .manufacturerName(UUID.randomUUID().toString());
    }
}
