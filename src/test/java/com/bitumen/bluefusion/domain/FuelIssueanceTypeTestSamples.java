package com.bitumen.bluefusion.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FuelIssueanceTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FuelIssueanceType getFuelIssueanceTypeSample1() {
        return new FuelIssueanceType().id(1L).fuelIssueTypeId(1L).fuelIssueType("fuelIssueType1");
    }

    public static FuelIssueanceType getFuelIssueanceTypeSample2() {
        return new FuelIssueanceType().id(2L).fuelIssueTypeId(2L).fuelIssueType("fuelIssueType2");
    }

    public static FuelIssueanceType getFuelIssueanceTypeRandomSampleGenerator() {
        return new FuelIssueanceType()
            .id(longCount.incrementAndGet())
            .fuelIssueTypeId(longCount.incrementAndGet())
            .fuelIssueType(UUID.randomUUID().toString());
    }
}
