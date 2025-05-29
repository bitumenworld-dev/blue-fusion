package com.bitumen.bluefusion.service.plantCategoryService.dto;

import com.bitumen.bluefusion.domain.PlantCategory;
import java.util.function.Function;

public interface PlantCategoryResponseMapper {
    Function<PlantCategory, PlantCategoryResponse> map = plantCategory ->
        new PlantCategoryResponse(
            plantCategory.getPlantCategoryId(),
            plantCategory.getPlantCategoryCode(),
            plantCategory.getPlantCategoryDescription()
        );
}
