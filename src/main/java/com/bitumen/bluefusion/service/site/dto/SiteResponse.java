package com.bitumen.bluefusion.service.site.dto;

public record SiteResponse(Long id, String siteName, String latitude, String longitude, String siteNotes, String company, byte[] image) {}
