package com.bitumen.bluefusion.service.site.dto;

public record SiteResponse(
    Long siteId,
    String siteName,
    String latitude,
    String longitude,
    String siteNotes,
    Boolean hasWorkshop,
    String company,
    byte[] image
) {}
