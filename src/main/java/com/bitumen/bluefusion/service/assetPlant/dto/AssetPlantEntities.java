package com.bitumen.bluefusion.service.assetPlant.dto;

import com.bitumen.bluefusion.domain.*;
import lombok.*;

@Builder
@Getter
@ToString
public class AssetPlantEntities {

    private final Company company;
    private final Make make;
    private final MakeModel makeModel;
    private final PlantCategory plantCategory;
    private final PlantSubcategory plantSubcategory;
}
