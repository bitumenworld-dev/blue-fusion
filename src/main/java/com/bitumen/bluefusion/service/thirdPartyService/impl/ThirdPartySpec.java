package com.bitumen.bluefusion.service.thirdPartyService.impl;

import com.bitumen.bluefusion.domain.ThirdParty;
import org.springframework.data.jpa.domain.Specification;

public interface ThirdPartySpec {
    static Specification<ThirdParty> withThirdPartyName(final String thirdPartyName) {
        return (root, query, builder) ->
            (thirdPartyName == null || thirdPartyName.isEmpty())
                ? builder.conjunction()
                : builder.like(root.get("thirdPartyName"), "%" + thirdPartyName + "%");
    }

    static Specification<ThirdParty> withThirdPartyShortName(final String thirdPartyShortName) {
        return (root, query, builder) ->
            (thirdPartyShortName == null || thirdPartyShortName.isEmpty())
                ? builder.conjunction()
                : builder.like(root.get("thirdPartyShortName"), "%" + thirdPartyShortName + "%");
    }

    static Specification<ThirdParty> withIsActive(final Boolean isActive) {
        return (root, query, builder) ->
            (isActive == null) ? builder.conjunction() : builder.equal(root.get("isActive"), "%" + isActive + "%");
    }
}
