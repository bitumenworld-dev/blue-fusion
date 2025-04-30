package com.bitumen.bluefusion.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SiteContractTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SiteContract getSiteContractSample1() {
        return new SiteContract().id(1L).siteContractId(1L).siteId(1L).contractId(1L);
    }

    public static SiteContract getSiteContractSample2() {
        return new SiteContract().id(2L).siteContractId(2L).siteId(2L).contractId(2L);
    }

    public static SiteContract getSiteContractRandomSampleGenerator() {
        return new SiteContract()
            .id(longCount.incrementAndGet())
            .siteContractId(longCount.incrementAndGet())
            .siteId(longCount.incrementAndGet())
            .contractId(longCount.incrementAndGet());
    }
}
