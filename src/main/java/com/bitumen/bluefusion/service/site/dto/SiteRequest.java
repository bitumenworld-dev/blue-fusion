package com.bitumen.bluefusion.service.site.dto;

public record SiteRequest(String siteName, String latitude, String longitude, String siteNotes, Long companyId, byte[] image) {}
