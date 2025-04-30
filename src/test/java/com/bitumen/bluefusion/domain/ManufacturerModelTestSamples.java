package com.bitumen.bluefusion.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ManufacturerModelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ManufacturerModel getManufacturerModelSample1() {
        return new ManufacturerModel().id(1L).modelId(1L).modelName("modelName1");
    }

    public static ManufacturerModel getManufacturerModelSample2() {
        return new ManufacturerModel().id(2L).modelId(2L).modelName("modelName2");
    }

    public static ManufacturerModel getManufacturerModelRandomSampleGenerator() {
        return new ManufacturerModel()
            .id(longCount.incrementAndGet())
            .modelId(longCount.incrementAndGet())
            .modelName(UUID.randomUUID().toString());
    }
}
