package com.bitumen.bluefusion.service.site.impl;

import com.bitumen.bluefusion.domain.Site;
import com.bitumen.bluefusion.service.site.dto.SiteResponse;
import java.util.function.Function;

interface SiteResponseMapper {
    Function<Site, SiteResponse> map = site ->
        new SiteResponse(
            site.getSiteId(),
            site.getSiteName(),
            site.getLatitude(),
            site.getLongitude(),
            site.getSiteNotes(),
            site.getCompany() == null ? "" : site.getCompany().getName(),
            site.getSiteImage()
        );
}
