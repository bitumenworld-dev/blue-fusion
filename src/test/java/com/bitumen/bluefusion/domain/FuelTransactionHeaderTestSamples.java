package com.bitumen.bluefusion.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FuelTransactionHeaderTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FuelTransactionHeader getFuelTransactionHeaderSample1() {
        return new FuelTransactionHeader()
            .id(1L)
            .fuelTransactionHeaderId(1L)
            .companyId(1L)
            .supplierId(1L)
            .transactionTypeId(1L)
            .orderNumber("orderNumber1")
            .deliveryNote("deliveryNote1")
            .grvNumber("grvNumber1")
            .invoiceNumber("invoiceNumber1")
            .note("note1")
            .registrationNumber("registrationNumber1")
            .attendeeId(1L)
            .operatorId(1L);
    }

    public static FuelTransactionHeader getFuelTransactionHeaderSample2() {
        return new FuelTransactionHeader()
            .id(2L)
            .fuelTransactionHeaderId(2L)
            .companyId(2L)
            .supplierId(2L)
            .transactionTypeId(2L)
            .orderNumber("orderNumber2")
            .deliveryNote("deliveryNote2")
            .grvNumber("grvNumber2")
            .invoiceNumber("invoiceNumber2")
            .note("note2")
            .registrationNumber("registrationNumber2")
            .attendeeId(2L)
            .operatorId(2L);
    }

    public static FuelTransactionHeader getFuelTransactionHeaderRandomSampleGenerator() {
        return new FuelTransactionHeader()
            .id(longCount.incrementAndGet())
            .fuelTransactionHeaderId(longCount.incrementAndGet())
            .companyId(longCount.incrementAndGet())
            .supplierId(longCount.incrementAndGet())
            .transactionTypeId(longCount.incrementAndGet())
            .orderNumber(UUID.randomUUID().toString())
            .deliveryNote(UUID.randomUUID().toString())
            .grvNumber(UUID.randomUUID().toString())
            .invoiceNumber(UUID.randomUUID().toString())
            .note(UUID.randomUUID().toString())
            .registrationNumber(UUID.randomUUID().toString())
            .attendeeId(longCount.incrementAndGet())
            .operatorId(longCount.incrementAndGet());
    }
}
