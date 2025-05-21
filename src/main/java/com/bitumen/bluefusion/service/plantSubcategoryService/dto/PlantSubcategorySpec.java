package com.bitumen.bluefusion.service.plantSubcategoryService.dto;

import com.bitumen.bluefusion.domain.FuelPump;
import com.bitumen.bluefusion.domain.PlantSubcategory;
import com.bitumen.bluefusion.domain.Storage;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public interface PlantSubcategorySpec {
    static Specification<PlantSubcategory> withPlantSubcategoryCode(final String plantSubcategoryCode) {
        return (
            (root, query, builder) ->
                (!StringUtils.hasText(plantSubcategoryCode))
                    ? builder.conjunction()
                    : builder.like(root.get("plantSubcategoryCode"), "%" + plantSubcategoryCode + "%")
        );
    }

    static Specification<PlantSubcategory> withPlantSubcategoryDescription(final String plantSubcategoryDescription) {
        return (
            (root, query, builder) ->
                (!StringUtils.hasText(plantSubcategoryDescription))
                    ? builder.conjunction()
                    : builder.like(root.get("plantSubcategoryDescription"), "%" + plantSubcategoryDescription + "%")
        );
    }
}
