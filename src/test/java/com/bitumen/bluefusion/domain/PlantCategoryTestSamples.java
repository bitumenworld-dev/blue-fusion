package com.bitumen.bluefusion.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PlantCategoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PlantCategory getPlantCategorySample1() {
        return new PlantCategory()
            .id(1L)
            .plantCategoryId(1L)
            .plantCategoryCode("plantCategoryCode1")
            .plantCategoryName("plantCategoryName1")
            .plantCategoryDescription("plantCategoryDescription1");
    }

    public static PlantCategory getPlantCategorySample2() {
        return new PlantCategory()
            .id(2L)
            .plantCategoryId(2L)
            .plantCategoryCode("plantCategoryCode2")
            .plantCategoryName("plantCategoryName2")
            .plantCategoryDescription("plantCategoryDescription2");
    }

    public static PlantCategory getPlantCategoryRandomSampleGenerator() {
        return new PlantCategory()
            .id(longCount.incrementAndGet())
            .plantCategoryId(longCount.incrementAndGet())
            .plantCategoryCode(UUID.randomUUID().toString())
            .plantCategoryName(UUID.randomUUID().toString())
            .plantCategoryDescription(UUID.randomUUID().toString());
    }
}
