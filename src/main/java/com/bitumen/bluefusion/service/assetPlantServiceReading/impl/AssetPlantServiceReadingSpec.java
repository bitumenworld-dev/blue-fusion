package com.bitumen.bluefusion.service.assetPlantServiceReading.impl;

import com.bitumen.bluefusion.domain.AssetPlant;
import com.bitumen.bluefusion.domain.AssetPlantServiceReading;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.data.jpa.domain.Specification;

public interface AssetPlantServiceReadingSpec {
    static Specification<AssetPlantServiceReading> withAssetPlantId(final AssetPlant assetPlant) {
        return (root, query, builder) ->
            (assetPlant == null) ? builder.conjunction() : builder.equal(root.get("assetPlantId"), assetPlant.getAssetPlantId());
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
    //    static Specification<AssetPlantServiceReading> withAssetPlantServiceReadingName(final String assetPlantServiceReadingName) {
    //        return (root, query, builder) ->
    //            (assetPlantServiceReadingName == null || assetPlantServiceReadingName.isEmpty())
    //                ? builder.conjunction()
    //                : builder.like(root.get("assetPlantServiceReadingName"), "%" + assetPlantServiceReadingName + "%");
    //    }
}
