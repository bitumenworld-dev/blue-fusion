package com.bitumen.bluefusion.service.site.dto;

public record SiteRequest(String siteName, String latitude, String longitude, Boolean hasWorkshop, String siteNotes, Long companyId) {}
