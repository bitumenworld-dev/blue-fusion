package com.bitumen.bluefusion.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PlantSubcategoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PlantSubcategory getPlantSubcategorySample1() {
        return new PlantSubcategory()
            .id(1L)
            .plantSubcategoryId(1L)
            .plantSubcategoryCode("plantSubcategoryCode1")
            .plantSubcategoryName("plantSubcategoryName1")
            .plantSubcategoryDescription("plantSubcategoryDescription1");
    }

    public static PlantSubcategory getPlantSubcategorySample2() {
        return new PlantSubcategory()
            .id(2L)
            .plantSubcategoryId(2L)
            .plantSubcategoryCode("plantSubcategoryCode2")
            .plantSubcategoryName("plantSubcategoryName2")
            .plantSubcategoryDescription("plantSubcategoryDescription2");
    }

    public static PlantSubcategory getPlantSubcategoryRandomSampleGenerator() {
        return new PlantSubcategory()
            .id(longCount.incrementAndGet())
            .plantSubcategoryId(longCount.incrementAndGet())
            .plantSubcategoryCode(UUID.randomUUID().toString())
            .plantSubcategoryName(UUID.randomUUID().toString())
            .plantSubcategoryDescription(UUID.randomUUID().toString());
    }
}
