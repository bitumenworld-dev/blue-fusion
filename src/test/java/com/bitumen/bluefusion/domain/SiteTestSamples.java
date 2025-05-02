//package com.bitumen.bluefusion.domain;
//
//import java.util.Random;
//import java.util.UUID;
//import java.util.concurrent.atomic.AtomicLong;
//
//public class SiteTestSamples {
//
//    private static final Random random = new Random();
//    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    public static Site getSiteSample1() {
//        return new Site()
//            .id(1L)
//            .siteId(1L)
//            .siteName("siteName1")
//            .latitude("latitude1")
//            .longitude("longitude1")
//            .siteNotes("siteNotes1")
//            .siteImageUrl("siteImageUrl1")
//            .companyId(1L);
//    }
//
//    public static Site getSiteSample2() {
//        return new Site()
//            .id(2L)
//            .siteId(2L)
//            .siteName("siteName2")
//            .latitude("latitude2")
//            .longitude("longitude2")
//            .siteNotes("siteNotes2")
//            .siteImageUrl("siteImageUrl2")
//            .companyId(2L);
//    }
//
//    public static Site getSiteRandomSampleGenerator() {
//        return new Site()
//            .id(longCount.incrementAndGet())
//            .siteId(longCount.incrementAndGet())
//            .siteName(UUID.randomUUID().toString())
//            .latitude(UUID.randomUUID().toString())
//            .longitude(UUID.randomUUID().toString())
//            .siteNotes(UUID.randomUUID().toString())
//            .siteImageUrl(UUID.randomUUID().toString())
//            .companyId(longCount.incrementAndGet());
//    }
//}
