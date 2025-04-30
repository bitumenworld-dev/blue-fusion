package com.bitumen.bluefusion.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AssetPlantPhotoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AssetPlantPhoto getAssetPlantPhotoSample1() {
        return new AssetPlantPhoto().id(1L).assetPlantPhotoId(1L).name("name1").assetPlantId(1L);
    }

    public static AssetPlantPhoto getAssetPlantPhotoSample2() {
        return new AssetPlantPhoto().id(2L).assetPlantPhotoId(2L).name("name2").assetPlantId(2L);
    }

    public static AssetPlantPhoto getAssetPlantPhotoRandomSampleGenerator() {
        return new AssetPlantPhoto()
            .id(longCount.incrementAndGet())
            .assetPlantPhotoId(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .assetPlantId(longCount.incrementAndGet());
    }
}
