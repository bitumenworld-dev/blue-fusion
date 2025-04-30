package com.bitumen.bluefusion.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ContractDivisionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ContractDivision getContractDivisionSample1() {
        return new ContractDivision()
            .id(1L)
            .contractDivisionId(1L)
            .contractDivisionNumber("contractDivisionNumber1")
            .companyId(1L)
            .buildSmartReference("buildSmartReference1");
    }

    public static ContractDivision getContractDivisionSample2() {
        return new ContractDivision()
            .id(2L)
            .contractDivisionId(2L)
            .contractDivisionNumber("contractDivisionNumber2")
            .companyId(2L)
            .buildSmartReference("buildSmartReference2");
    }

    public static ContractDivision getContractDivisionRandomSampleGenerator() {
        return new ContractDivision()
            .id(longCount.incrementAndGet())
            .contractDivisionId(longCount.incrementAndGet())
            .contractDivisionNumber(UUID.randomUUID().toString())
            .companyId(longCount.incrementAndGet())
            .buildSmartReference(UUID.randomUUID().toString());
    }
}
