package com.bitumen.bluefusion.service.plantCategoryService.impl;

import com.bitumen.bluefusion.domain.PlantCategory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public interface PlantCategorySpec {
    static Specification<PlantCategory> withPlantCategoryCode(final String plantCategoryCode) {
        return (
            (root, query, builder) ->
                (!StringUtils.hasText(plantCategoryCode))
                    ? builder.conjunction()
                    : builder.like(root.get("plantCategoryCode"), "%" + plantCategoryCode + "%")
        );
    }

    static Specification<PlantCategory> withPlantCategoryDescription(final String plantCategoryDescription) {
        return (
            (root, query, builder) ->
                (!StringUtils.hasText(plantCategoryDescription))
                    ? builder.conjunction()
                    : builder.like(root.get("plantCategoryDescription"), "%" + plantCategoryDescription + "%")
        );
    }
}
