package com.bitumen.bluefusion.service.assetPlantServiceReading.impl;

import com.bitumen.bluefusion.domain.AssetPlantServiceReading;
import org.springframework.data.jpa.domain.Specification;

public interface AssetPlantServiceReadingSpec {
    static Specification<AssetPlantServiceReading> withAssetPlantId(final Long assetPlantId) {
        return (root, query, builder) ->
            (assetPlantId == null) ? builder.conjunction() : builder.equal(root.get("assetPlantId"), assetPlantId);
    }

    static Specification<AssetPlantServiceReading> withIsActive(final Boolean isActive) {
        return (root, query, builder) -> (isActive == null) ? builder.conjunction() : builder.equal(root.get("isActive"), isActive);
    }

    static Specification<AssetPlantServiceReading> withServiceUnit(final String serviceUnit) {
        return (root, query, builder) ->
            (serviceUnit == null || serviceUnit.isEmpty())
                ? builder.conjunction()
                : builder.like(root.get("serviceUnit"), "%" + serviceUnit + "%");
    }
}
