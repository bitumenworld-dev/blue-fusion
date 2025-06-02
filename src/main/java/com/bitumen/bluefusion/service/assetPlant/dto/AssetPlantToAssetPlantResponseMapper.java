package com.bitumen.bluefusion.service.assetPlant.dto;

import com.bitumen.bluefusion.domain.AssetPlant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssetPlantToAssetPlantResponseMapper {
    @Mapping(source = "assetPlant.make.make", target = "make")
    @Mapping(source = "assetPlant.model.model", target = "model")
    @Mapping(source = "assetPlant.plantCategory.plantCategoryDescription", target = "plantCategory")
    @Mapping(source = "assetPlant.plantSubcategory.plantSubcategoryDescription", target = "plantSubcategory")
    @Mapping(source = "assetPlant.owner.name", target = "owner")
    AssetPlantResponse map(AssetPlant assetPlant);
}
