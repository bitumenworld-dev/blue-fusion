//package com.bitumen.bluefusion.domain;
//
//import java.util.Random;
//import java.util.UUID;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.concurrent.atomic.AtomicLong;
//
//public class AssetPlantTestSamples {
//
//    private static final Random random = new Random();
//    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));
//
//    public static AssetPlant getAssetPlantSample1() {
//        return new AssetPlant()
//            .id(1L)
//            .assetPlantId(1L)
//            .fleetNumber("fleetNumber1")
//            .numberPlate("numberPlate1")
//            .fleetDescription("fleetDescription1")
//            .ownerId(1L)
//            .accessibleByCompany("accessibleByCompany1")
//            .plantCategoryId(1L)
//            .plantSubcategoryId("plantSubcategoryId1")
//            .manufacturerId(1L)
//            .modelId(1L)
//            .yearOfManufacture(1)
//            .colour("colour1")
//            .currentSmrIndex(1)
//            .engineNumber("engineNumber1")
//            .engineCapacityCc("engineCapacityCc1")
//            .currentSiteId(1L)
//            .currentContractId(1L)
//            .currentOperatorId(1L)
//            .ledgerCode("ledgerCode1")
//            .chassisNumber("chassisNumber1")
//            .currentLocation("currentLocation1");
//    }
//
//    public static AssetPlant getAssetPlantSample2() {
//        return new AssetPlant()
//            .id(2L)
//            .assetPlantId(2L)
//            .fleetNumber("fleetNumber2")
//            .numberPlate("numberPlate2")
//            .fleetDescription("fleetDescription2")
//            .ownerId(2L)
//            .accessibleByCompany("accessibleByCompany2")
//            .plantCategoryId(2L)
//            .plantSubcategoryId("plantSubcategoryId2")
//            .manufacturerId(2L)
//            .modelId(2L)
//            .yearOfManufacture(2)
//            .colour("colour2")
//            .currentSmrIndex(2)
//            .engineNumber("engineNumber2")
//            .engineCapacityCc("engineCapacityCc2")
//            .currentSiteId(2L)
//            .currentContractId(2L)
//            .currentOperatorId(2L)
//            .ledgerCode("ledgerCode2")
//            .chassisNumber("chassisNumber2")
//            .currentLocation("currentLocation2");
//    }
//
//    public static AssetPlant getAssetPlantRandomSampleGenerator() {
//        return new AssetPlant()
//            .id(longCount.incrementAndGet())
//            .assetPlantId(longCount.incrementAndGet())
//            .fleetNumber(UUID.randomUUID().toString())
//            .numberPlate(UUID.randomUUID().toString())
//            .fleetDescription(UUID.randomUUID().toString())
//            .ownerId(longCount.incrementAndGet())
//            .accessibleByCompany(UUID.randomUUID().toString())
//            .plantCategoryId(longCount.incrementAndGet())
//            .plantSubcategoryId(UUID.randomUUID().toString())
//            .manufacturerId(longCount.incrementAndGet())
//            .modelId(longCount.incrementAndGet())
//            .yearOfManufacture(intCount.incrementAndGet())
//            .colour(UUID.randomUUID().toString())
//            .currentSmrIndex(intCount.incrementAndGet())
//            .engineNumber(UUID.randomUUID().toString())
//            .engineCapacityCc(UUID.randomUUID().toString())
//            .currentSiteId(longCount.incrementAndGet())
//            .currentContractId(longCount.incrementAndGet())
//            .currentOperatorId(longCount.incrementAndGet())
//            .ledgerCode(UUID.randomUUID().toString())
//            .chassisNumber(UUID.randomUUID().toString())
//            .currentLocation(UUID.randomUUID().toString());
//    }
//}
