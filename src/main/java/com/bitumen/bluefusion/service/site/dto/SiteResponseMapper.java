package com.bitumen.bluefusion.service.site.dto;

import com.bitumen.bluefusion.domain.Site;
import java.util.function.Function;

public interface SiteResponseMapper {
    Function<Site, SiteResponse> map = site ->
        new SiteResponse(
            site.getSiteId(),
            site.getSiteName(),
            site.getLatitude(),
            site.getLongitude(),
            site.getSiteNotes(),
            site.getHasWorkshop(),
            site.getCompany() == null ? "" : site.getCompany().getName(),
            site.getSiteImage()
        );
}
