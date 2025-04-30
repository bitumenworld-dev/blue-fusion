package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.domain.PlantCategory;
import com.bitumen.bluefusion.repository.PlantCategoryRepository;
import com.bitumen.bluefusion.service.PlantCategoryService;
import com.bitumen.bluefusion.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bitumen.bluefusion.domain.PlantCategory}.
 */
@RestController
@RequestMapping("/api/plant-categories")
public class PlantCategoryResource {

    private static final Logger LOG = LoggerFactory.getLogger(PlantCategoryResource.class);

    private static final String ENTITY_NAME = "plantCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlantCategoryService plantCategoryService;

    private final PlantCategoryRepository plantCategoryRepository;

    public PlantCategoryResource(PlantCategoryService plantCategoryService, PlantCategoryRepository plantCategoryRepository) {
        this.plantCategoryService = plantCategoryService;
        this.plantCategoryRepository = plantCategoryRepository;
    }

    /**
     * {@code POST  /plant-categories} : Create a new plantCategory.
     *
     * @param plantCategory the plantCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plantCategory, or with status {@code 400 (Bad Request)} if the plantCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PlantCategory> createPlantCategory(@RequestBody PlantCategory plantCategory) throws URISyntaxException {
        LOG.debug("REST request to save PlantCategory : {}", plantCategory);
        if (plantCategory.getId() != null) {
            throw new BadRequestAlertException("A new plantCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        plantCategory = plantCategoryService.save(plantCategory);
        return ResponseEntity.created(new URI("/api/plant-categories/" + plantCategory.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, plantCategory.getId().toString()))
            .body(plantCategory);
    }

    /**
     * {@code PUT  /plant-categories/:id} : Updates an existing plantCategory.
     *
     * @param id the id of the plantCategory to save.
     * @param plantCategory the plantCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plantCategory,
     * or with status {@code 400 (Bad Request)} if the plantCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plantCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlantCategory> updatePlantCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlantCategory plantCategory
    ) throws URISyntaxException {
        LOG.debug("REST request to update PlantCategory : {}, {}", id, plantCategory);
        if (plantCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plantCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plantCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        plantCategory = plantCategoryService.update(plantCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plantCategory.getId().toString()))
            .body(plantCategory);
    }

    /**
     * {@code PATCH  /plant-categories/:id} : Partial updates given fields of an existing plantCategory, field will ignore if it is null
     *
     * @param id the id of the plantCategory to save.
     * @param plantCategory the plantCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plantCategory,
     * or with status {@code 400 (Bad Request)} if the plantCategory is not valid,
     * or with status {@code 404 (Not Found)} if the plantCategory is not found,
     * or with status {@code 500 (Internal Server Error)} if the plantCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlantCategory> partialUpdatePlantCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlantCategory plantCategory
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PlantCategory partially : {}, {}", id, plantCategory);
        if (plantCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plantCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plantCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlantCategory> result = plantCategoryService.partialUpdate(plantCategory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plantCategory.getId().toString())
        );
    }

    /**
     * {@code GET  /plant-categories} : get all the plantCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plantCategories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PlantCategory>> getAllPlantCategories(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of PlantCategories");
        Page<PlantCategory> page = plantCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plant-categories/:id} : get the "id" plantCategory.
     *
     * @param id the id of the plantCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plantCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlantCategory> getPlantCategory(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PlantCategory : {}", id);
        Optional<PlantCategory> plantCategory = plantCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plantCategory);
    }

    /**
     * {@code DELETE  /plant-categories/:id} : delete the "id" plantCategory.
     *
     * @param id the id of the plantCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlantCategory(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PlantCategory : {}", id);
        plantCategoryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
