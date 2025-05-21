package com.bitumen.bluefusion.service.plantSubcategoryService.imp;

import com.bitumen.bluefusion.domain.FuelPump;
import com.bitumen.bluefusion.domain.PlantSubcategory;
import com.bitumen.bluefusion.domain.Storage;
import com.bitumen.bluefusion.repository.PlantSubcategoryRepository;
import com.bitumen.bluefusion.service.exceptions.RecordNotFoundException;
import com.bitumen.bluefusion.service.fuelPump.impl.FuelPumpSpec;
import com.bitumen.bluefusion.service.plantSubcategoryService.PlantSubcategoryService;
import com.bitumen.bluefusion.service.plantSubcategoryService.dto.PlantSubcategoryRequest;
import com.bitumen.bluefusion.service.plantSubcategoryService.dto.PlantSubcategoryResponse;
import com.bitumen.bluefusion.service.plantSubcategoryService.dto.PlantSubcategoryResponseMapper;
import com.bitumen.bluefusion.service.plantSubcategoryService.dto.PlantSubcategorySpec;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.PlantSubcategory}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PlantSubcategoryServiceImpl implements PlantSubcategoryService {

    private final PlantSubcategoryRepository plantSubcategoryRepository;

    @Override
    public PlantSubcategoryResponse save(PlantSubcategoryRequest plantSubcategoryRequest) {
        PlantSubcategory subcategory = PlantSubcategory.builder()
            .plantSubcategoryCode(plantSubcategoryRequest.plantSubcategoryCode())
            .plantSubcategoryDescription(plantSubcategoryRequest.plantSubcategoryDescription())
            .build();
        subcategory = plantSubcategoryRepository.save(subcategory);
        return PlantSubcategoryResponseMapper.map.apply(subcategory);
    }

    @Override
    public PlantSubcategoryResponse update(Long subcategoryId, PlantSubcategoryRequest plantSubcategoryRequest) {
        PlantSubcategory subcategory = plantSubcategoryRepository
            .findById(subcategoryId)
            .orElseThrow(() -> new RecordNotFoundException(String.format("PlantSubcategory with id %d does not exist", subcategoryId)));
        subcategory.setPlantSubcategoryCode(plantSubcategoryRequest.plantSubcategoryCode());
        subcategory.setPlantSubcategoryDescription(plantSubcategoryRequest.plantSubcategoryDescription());
        subcategory = plantSubcategoryRepository.save(subcategory);
        return PlantSubcategoryResponseMapper.map.apply(subcategory);
    }

    @Override
    public PlantSubcategoryResponse partialUpdate(Long subcategoryId, PlantSubcategoryRequest plantSubcategoryRequest) {
        return plantSubcategoryRepository
            .findById(subcategoryId)
            .map(existingPlantSubcategory -> {
                Optional.ofNullable(plantSubcategoryRequest.plantSubcategoryCode()).ifPresent(
                    existingPlantSubcategory::setPlantSubcategoryCode
                );
                Optional.ofNullable(plantSubcategoryRequest.plantSubcategoryDescription()).ifPresent(
                    existingPlantSubcategory::setPlantSubcategoryDescription
                );
                return existingPlantSubcategory;
            })
            .map(plantSubcategoryRepository::save)
            .map(PlantSubcategoryResponseMapper.map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("PlantSubcategory with id %s not found", subcategoryId)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlantSubcategoryResponse> findAll(Pageable pageable, String plantSubcategoryCode, String planSubcategoryDescription) {
        Specification<PlantSubcategory> specification = PlantSubcategorySpec.withPlantSubcategoryCode(plantSubcategoryCode).and(
            PlantSubcategorySpec.withPlantSubcategoryDescription(planSubcategoryDescription)
        );

        return plantSubcategoryRepository.findAll(specification, pageable).map(PlantSubcategoryResponseMapper.map);
    }

    @Override
    @Transactional(readOnly = true)
    public PlantSubcategoryResponse findOne(Long id) {
        return plantSubcategoryRepository
            .findById(id)
            .map(PlantSubcategoryResponseMapper.map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("PlantSubcategory with id %d does not exist", id)));
    }

    @Override
    public void delete(Long id) {
        plantSubcategoryRepository
            .findById(id)
            .ifPresentOrElse(plantSubcategoryRepository::delete, () -> {
                throw new RecordNotFoundException(String.format("PlantSubcategory with id %d does not exist", id));
            });
    }
}
