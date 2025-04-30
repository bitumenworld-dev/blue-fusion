package com.bitumen.bluefusion.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AssetPlantServiceReadingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AssetPlantServiceReading getAssetPlantServiceReadingSample1() {
        return new AssetPlantServiceReading().id(1L).assetPlantServiceReadingId(1L).assetPlantId(1L);
    }

    public static AssetPlantServiceReading getAssetPlantServiceReadingSample2() {
        return new AssetPlantServiceReading().id(2L).assetPlantServiceReadingId(2L).assetPlantId(2L);
    }

    public static AssetPlantServiceReading getAssetPlantServiceReadingRandomSampleGenerator() {
        return new AssetPlantServiceReading()
            .id(longCount.incrementAndGet())
            .assetPlantServiceReadingId(longCount.incrementAndGet())
            .assetPlantId(longCount.incrementAndGet());
    }
}
