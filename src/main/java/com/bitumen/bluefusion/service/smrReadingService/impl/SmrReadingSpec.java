package com.bitumen.bluefusion.service.smrReadingService.impl;

import com.bitumen.bluefusion.domain.SmrReading;
import org.springframework.data.jpa.domain.Specification;

public interface SmrReadingSpec {
    static Specification<SmrReading> withAssetPlantId(final Long assetPlantId) {
        return (root, query, builder) ->
            (assetPlantId == null) ? builder.conjunction() : builder.equal(root.get("assetPlant").get("id"), assetPlantId);
    }

    static Specification<SmrReading> withUnit(final String unit) {
        return (root, query, builder) ->
            (unit == null || unit.isEmpty()) ? builder.conjunction() : builder.like(root.get("unit"), "%" + unit + "%");
    }

    static Specification<SmrReading> withSmrReading(final Double smrReading) {
        return (root, query, builder) -> (smrReading == null) ? builder.conjunction() : builder.equal(root.get("smrReading"), smrReading);
    }
}
