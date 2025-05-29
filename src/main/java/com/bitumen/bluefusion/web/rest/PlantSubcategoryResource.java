package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.service.plantSubcategoryService.PlantSubcategoryService;
import com.bitumen.bluefusion.service.plantSubcategoryService.dto.PlantSubcategoryRequest;
import com.bitumen.bluefusion.service.plantSubcategoryService.dto.PlantSubcategoryResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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

/**
 * REST controller for managing {@link com.bitumen.bluefusion.domain.PlantSubcategory}.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/plant-subcategories")
public class PlantSubcategoryResource {

    private static final String ENTITY_NAME = "plantSubcategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlantSubcategoryService plantSubcategoryService;

    @PostMapping("")
    public ResponseEntity<PlantSubcategoryResponse> createPlantSubcategory(@RequestBody PlantSubcategoryRequest plantSubcategoryRequest)
        throws URISyntaxException {
        PlantSubcategoryResponse plantSubcategory = plantSubcategoryService.save(plantSubcategoryRequest);
        return ResponseEntity.created(new URI("/api/plant-subcategories/" + plantSubcategory.id()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, plantSubcategory.id().toString()))
            .body(plantSubcategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantSubcategoryResponse> updatePlantSubcategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlantSubcategoryRequest plantSubcategoryRequest
    ) {
        PlantSubcategoryResponse plantSubcategory = plantSubcategoryService.update(id, plantSubcategoryRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plantSubcategory.id().toString()))
            .body(plantSubcategory);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlantSubcategoryResponse> partialUpdatePlantSubcategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlantSubcategoryRequest plantSubcategoryRequest
    ) {
        PlantSubcategoryResponse result = plantSubcategoryService.partialUpdate(id, plantSubcategoryRequest);
        return ResponseUtil.wrapOrNotFound(
            Optional.of(result),
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<PlantSubcategoryResponse>> getAllPlantSubcategories(
        @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
        @RequestParam(value = "size", defaultValue = "100", required = false) Integer size,
        @RequestParam(value = "subcategoryCode", required = false) String subcategoryCode,
        @RequestParam(value = "subcategoryDescription", required = false) String subcategoryDescription
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("plantSubcategoryId").descending());
        Page<PlantSubcategoryResponse> subcategoryResponses = plantSubcategoryService.findAll(
            pageable,
            subcategoryCode,
            subcategoryDescription
        );
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(),
            subcategoryResponses
        );

        return ResponseEntity.ok().headers(headers).body(subcategoryResponses.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantSubcategoryResponse> getPlantSubcategory(@PathVariable("id") Long id) {
        PlantSubcategoryResponse plantSubcategory = plantSubcategoryService.findOne(id);
        return new ResponseEntity<>(plantSubcategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlantSubcategory(@PathVariable("id") Long id) {
        plantSubcategoryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
