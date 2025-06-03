package com.bitumen.bluefusion.service.assetPlant.imp;

import com.bitumen.bluefusion.domain.*;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public interface AssetPlantSpec {
    static Specification<AssetPlant> isActive(final Boolean isActive) {
        return (
            (root, query, builder) -> (Objects.isNull(isActive)) ? builder.conjunction() : builder.equal(root.get("isActive"), isActive)
        );
    }

    static Specification<AssetPlant> withFleetNumberLike(final String fleetNumber) {
        return (
            (root, query, builder) ->
                (!StringUtils.hasText(fleetNumber)) ? builder.conjunction() : builder.like(root.get("fleetNumber"), "%" + fleetNumber + "%")
        );
    }

    static Specification<AssetPlant> withMake(final Make make) {
        return ((root, query, builder) -> (Objects.isNull(make)) ? builder.conjunction() : builder.equal(root.get("make"), make));
    }

    static Specification<AssetPlant> withModel(final MakeModel model) {
        return ((root, query, builder) -> (Objects.isNull(model)) ? builder.conjunction() : builder.equal(root.get("model"), model));
    }

    static Specification<AssetPlant> withPlantCategory(final PlantCategory plantCategory) {
        return (
            (root, query, builder) ->
                (Objects.isNull(plantCategory)) ? builder.conjunction() : builder.equal(root.get("plantCategory"), plantCategory)
        );
    }

    static Specification<AssetPlant> withPlantSubCategory(final PlantSubcategory plantSubcategory) {
        return (
            (root, query, builder) ->
                (Objects.isNull(plantSubcategory)) ? builder.conjunction() : builder.equal(root.get("plantSubcategory"), plantSubcategory)
        );
    }

    static Specification<AssetPlant> withTrackConsumption(final Boolean trackConsumption) {
        return (
            (root, query, builder) ->
                (Objects.isNull(trackConsumption)) ? builder.conjunction() : builder.equal(root.get("trackConsumption"), trackConsumption)
        );
    }

    static Specification<AssetPlant> withTrackSmrReading(final Boolean trackSmrReading) {
        return (
            (root, query, builder) ->
                (Objects.isNull(trackSmrReading)) ? builder.conjunction() : builder.equal(root.get("trackSmrReading"), trackSmrReading)
        );
    }

    static Specification<AssetPlant> withCompany(final Company company) {
        return ((root, query, builder) -> (Objects.isNull(company)) ? builder.conjunction() : builder.equal(root.get("owner"), company));
    }
}
