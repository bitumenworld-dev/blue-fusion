package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.domain.PlantCategory;
import com.bitumen.bluefusion.repository.PlantCategoryRepository;
import com.bitumen.bluefusion.service.plantCategoryService.PlantCategoryService;
import com.bitumen.bluefusion.service.plantCategoryService.dto.PlantCategoryRequest;
import com.bitumen.bluefusion.service.plantCategoryService.dto.PlantCategoryResponse;
import com.bitumen.bluefusion.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/plant-categories")
public class PlantCategoryResource {

    private static final String ENTITY_NAME = "plantCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlantCategoryService plantCategoryService;

    @PostMapping("")
    public ResponseEntity<PlantCategoryResponse> createPlantCategory(@RequestBody PlantCategoryRequest plantCategoryRequest)
        throws URISyntaxException {
        PlantCategoryResponse plantCategory = plantCategoryService.save(plantCategoryRequest);
        return ResponseEntity.created(new URI("/api/plant-categories/" + plantCategory.id()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, plantCategory.id().toString()))
            .body(plantCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantCategoryResponse> updatePlantCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlantCategoryRequest plantCategoryRequest
    ) {
        PlantCategoryResponse plantCategory = plantCategoryService.update(id, plantCategoryRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plantCategory.id().toString()))
            .body(plantCategory);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlantCategoryResponse> partialUpdatePlantCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlantCategoryRequest plantCategoryRequest
    ) {
        PlantCategoryResponse result = plantCategoryService.partialUpdate(id, plantCategoryRequest);

        return ResponseUtil.wrapOrNotFound(
            Optional.ofNullable(result),
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.id().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<PlantCategoryResponse>> getAllPlantCategories(
        @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
        @RequestParam(value = "size", defaultValue = "100", required = false) Integer size,
        @RequestParam(value = "plantCategoryCode", required = false) String plantCategoryCode,
        @RequestParam(value = "plantCategoryDescription", required = false) String plantCategoryDescription
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("plantCategoryId").descending());
        Page<PlantCategoryResponse> plantCategoryResponsePage = plantCategoryService.findAll(
            pageable,
            plantCategoryCode,
            plantCategoryDescription
        );
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(),
            plantCategoryResponsePage
        );

        return ResponseEntity.ok().headers(headers).body(plantCategoryResponsePage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantCategoryResponse> getPlantCategory(@PathVariable("id") Long id) {
        PlantCategoryResponse plantCategory = plantCategoryService.findOne(id);
        return new ResponseEntity<>(plantCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlantCategory(@PathVariable("id") Long id) {
        plantCategoryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
