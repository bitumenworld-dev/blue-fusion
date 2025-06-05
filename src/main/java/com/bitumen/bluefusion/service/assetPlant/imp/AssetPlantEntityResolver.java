package com.bitumen.bluefusion.service.assetPlant.imp;

import com.bitumen.bluefusion.repository.*;
import com.bitumen.bluefusion.service.assetPlant.dto.AssetPlantEntities;
import com.bitumen.bluefusion.service.assetPlant.dto.AssetPlantFilterCriteria;
import com.bitumen.bluefusion.service.assetPlant.payload.AssetPlantRequest;
import com.bitumen.bluefusion.service.exceptions.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AssetPlantEntityResolver {

    private final CompanyRepository companyRepository;
    private final MakeRepository makeRepository;
    private final MakeModelRepository makeModelRepository;
    private final PlantCategoryRepository plantCategoryRepository;
    private final PlantSubcategoryRepository plantSubcategoryRepository;

    public AssetPlantEntities resolveEntitiesForRequest(AssetPlantRequest request) {
        return AssetPlantEntities.builder()
            .company(resolveEntityWithValidation(request.ownerId(), companyRepository, "Company"))
            .make(resolveEntityWithValidation(request.makeId(), makeRepository, "Make"))
            .makeModel(resolveEntityWithValidation(request.modelId(), makeModelRepository, "Make model"))
            .plantCategory(resolveEntityWithValidation(request.plantCategoryId(), plantCategoryRepository, "Plant category"))
            .plantSubcategory(resolveEntityWithValidation(request.plantSubcategoryId(), plantSubcategoryRepository, "Plant subcategory"))
            .build();
    }

    public AssetPlantEntities resolveEntitiesForCriteria(AssetPlantFilterCriteria criteria) {
        return AssetPlantEntities.builder()
            .company(resolveEntityOptional(criteria.companyId(), companyRepository))
            .make(resolveEntityOptional(criteria.makeId(), makeRepository))
            .makeModel(resolveEntityOptional(criteria.modelId(), makeModelRepository))
            .plantCategory(resolveEntityOptional(criteria.categoryId(), plantCategoryRepository))
            .plantSubcategory(resolveEntityOptional(criteria.subcategoryId(), plantSubcategoryRepository))
            .build();
    }

    private <T> T resolveEntityWithValidation(Long id, JpaRepository<T, Long> repository, String entityName) {
        if (id == null) {
            return null;
        }
        return repository
            .findById(id)
            .orElseThrow(() -> new RecordNotFoundException(String.format("%s with id %d not found", entityName, id)));
    }

    private <T> T resolveEntityOptional(Long id, JpaRepository<T, Long> repository) {
        return id != null ? repository.findById(id).orElse(null) : null;
    }
}
