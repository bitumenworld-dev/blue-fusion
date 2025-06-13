package com.bitumen.bluefusion.service.site.impl;

import com.bitumen.bluefusion.domain.Company;
import com.bitumen.bluefusion.domain.Site;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public interface SiteSpec {
    static Specification<Site> withCompany(final Company company) {
        return ((root, query, builder) -> (Objects.isNull(company)) ? builder.conjunction() : builder.equal(root.get("company"), company));
    }

    static Specification<Site> withSiteName(final String siteName) {
        return (
            (root, query, builder) ->
                (!StringUtils.hasText(siteName)) ? builder.conjunction() : builder.like(root.get("siteName"), "%" + siteName + "%")
        );
    }

    static Specification<Site> isActive(final Boolean isFixed) {
        return ((root, query, builder) -> (Objects.isNull(isFixed)) ? builder.conjunction() : builder.equal(root.get("isActive"), isFixed));
    }

    static Specification<Site> hasWorkshop(final Boolean hasWorkshop) {
        return (
            (root, query, builder) ->
                (Objects.isNull(hasWorkshop)) ? builder.conjunction() : builder.equal(root.get("hasWorkshop"), hasWorkshop)
        );
    }
}
