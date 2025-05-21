package com.bitumen.bluefusion.service.site.dto;

import org.springframework.web.multipart.MultipartFile;

public record SiteRequest(String siteName, String latitude, String longitude, String siteNotes, Long companyId) {}
