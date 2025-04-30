package com.bitumen.bluefusion.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WorkshopTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Workshop getWorkshopSample1() {
        return new Workshop().id(1L).workshopId(1L).siteId(1L).workshopName("workshopName1");
    }

    public static Workshop getWorkshopSample2() {
        return new Workshop().id(2L).workshopId(2L).siteId(2L).workshopName("workshopName2");
    }

    public static Workshop getWorkshopRandomSampleGenerator() {
        return new Workshop()
            .id(longCount.incrementAndGet())
            .workshopId(longCount.incrementAndGet())
            .siteId(longCount.incrementAndGet())
            .workshopName(UUID.randomUUID().toString());
    }
}
