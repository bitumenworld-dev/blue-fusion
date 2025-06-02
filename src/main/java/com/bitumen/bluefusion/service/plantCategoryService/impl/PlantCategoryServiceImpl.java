package com.bitumen.bluefusion.service.plantCategoryService.impl;

import com.bitumen.bluefusion.domain.PlantCategory;
import com.bitumen.bluefusion.repository.PlantCategoryRepository;
import com.bitumen.bluefusion.service.exceptions.RecordNotFoundException;
import com.bitumen.bluefusion.service.plantCategoryService.PlantCategoryService;
import com.bitumen.bluefusion.service.plantCategoryService.dto.PlantCategoryRequest;
import com.bitumen.bluefusion.service.plantCategoryService.dto.PlantCategoryResponse;
import com.bitumen.bluefusion.service.plantCategoryService.dto.PlantCategoryResponseMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PlantCategoryServiceImpl implements PlantCategoryService {

    private final PlantCategoryRepository plantCategoryRepository;

    @Override
    public PlantCategoryResponse save(PlantCategoryRequest plantCategoryRequest) {
        PlantCategory plantCategory = PlantCategory.builder()
            .plantCategoryCode(plantCategoryRequest.plantCategoryCode())
            .plantCategoryDescription(plantCategoryRequest.plantCategoryDescription())
            .build();
        plantCategory = plantCategoryRepository.save(plantCategory);
        return PlantCategoryResponseMapper.map.apply(plantCategory);
    }

    @Override
    public PlantCategoryResponse update(Long plantCategoryId, PlantCategoryRequest plantCategoryRequest) {
        PlantCategory plantCategory = plantCategoryRepository
            .findById(plantCategoryId)
            .orElseThrow(() -> new RecordNotFoundException(String.format("PlantCategory with id %s not found", plantCategoryId)));
        plantCategory.setPlantCategoryCode(plantCategoryRequest.plantCategoryCode());
        plantCategory.setPlantCategoryDescription(plantCategoryRequest.plantCategoryDescription());
        plantCategory = plantCategoryRepository.save(plantCategory);
        return PlantCategoryResponseMapper.map.apply(plantCategory);
    }

    @Override
    public PlantCategoryResponse partialUpdate(Long plantCategoryId, PlantCategoryRequest plantCategoryRequest) {
        return plantCategoryRepository
            .findById(plantCategoryId)
            .map(existingPlantCategory -> {
                Optional.ofNullable(plantCategoryRequest.plantCategoryCode()).ifPresent(existingPlantCategory::setPlantCategoryCode);
                Optional.ofNullable(plantCategoryRequest.plantCategoryDescription()).ifPresent(
                    existingPlantCategory::setPlantCategoryDescription
                );
                return existingPlantCategory;
            })
            .map(plantCategoryRepository::save)
            .map(PlantCategoryResponseMapper.map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("PlantCategory with id %s not found", plantCategoryId)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlantCategoryResponse> findAll(Pageable pageable, String plantCategoryCode, String plantCategoryDescription) {
        Specification<PlantCategory> specification = PlantCategorySpec.withPlantCategoryCode(plantCategoryCode).and(
            PlantCategorySpec.withPlantCategoryDescription(plantCategoryDescription)
        );
        return plantCategoryRepository.findAll(specification, pageable).map(PlantCategoryResponseMapper.map);
    }

    @Override
    @Transactional(readOnly = true)
    public PlantCategoryResponse findOne(Long id) {
        return plantCategoryRepository
            .findById(id)
            .map(PlantCategoryResponseMapper.map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("PlantCategory with id %s not found", id)));
    }

    @Override
    public void delete(Long id) {
        plantCategoryRepository
            .findById(id)
            .ifPresentOrElse(plantCategoryRepository::delete, () -> {
                throw new RecordNotFoundException(String.format("Plant category with id %d does not exist", id));
            });
    }
}
