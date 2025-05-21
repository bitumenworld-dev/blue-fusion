package com.bitumen.bluefusion.service.plantSubcategoryService.dto;

import com.bitumen.bluefusion.domain.PlantSubcategory;
import java.util.function.Function;

public interface PlantSubcategoryResponseMapper {
    Function<PlantSubcategory, PlantSubcategoryResponse> map = plantSubcategory ->
        new PlantSubcategoryResponse(
            plantSubcategory.getPlantSubcategoryId(),
            plantSubcategory.getPlantSubcategoryCode(),
            plantSubcategory.getPlantSubcategoryDescription()
        );
}
