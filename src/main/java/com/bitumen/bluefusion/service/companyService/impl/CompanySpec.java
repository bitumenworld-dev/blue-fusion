package com.bitumen.bluefusion.service.companyService.impl;

import com.bitumen.bluefusion.domain.Company;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

interface CompanySpec {
    static Specification<Company> withCompanyNameLike(final String companyName) {
        return (
            (root, query, builder) ->
                (!StringUtils.hasText(companyName)) ? builder.conjunction() : builder.like(root.get("name"), "%" + companyName + "%")
        );
    }

    static Specification<Company> isActive(final Boolean isActive) {
        return (
            (root, query, builder) -> (Objects.isNull(isActive)) ? builder.conjunction() : builder.equal(root.get("isActive"), isActive)
        );
    }

    static Specification<Company> isIAC(final Boolean isActive) {
        return ((root, query, builder) -> (Objects.isNull(isActive)) ? builder.conjunction() : builder.equal(root.get("isIAC"), isActive));
    }

    static Specification<Company> usesFuelSystem(final Boolean usesFuelSystem) {
        return (
            (root, query, builder) ->
                (Objects.isNull(usesFuelSystem)) ? builder.conjunction() : builder.equal(root.get("usesFuelSystem"), usesFuelSystem)
        );
    }
}
