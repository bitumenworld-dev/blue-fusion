package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.domain.PlantSubcategory;
import com.bitumen.bluefusion.repository.PlantSubcategoryRepository;
import com.bitumen.bluefusion.service.PlantSubcategoryService;
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
 * REST controller for managing {@link com.bitumen.bluefusion.domain.PlantSubcategory}.
 */
@RestController
@RequestMapping("/api/plant-subcategories")
public class PlantSubcategoryResource {

    private static final Logger LOG = LoggerFactory.getLogger(PlantSubcategoryResource.class);

    private static final String ENTITY_NAME = "plantSubcategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlantSubcategoryService plantSubcategoryService;

    private final PlantSubcategoryRepository plantSubcategoryRepository;

    public PlantSubcategoryResource(
        PlantSubcategoryService plantSubcategoryService,
        PlantSubcategoryRepository plantSubcategoryRepository
    ) {
        this.plantSubcategoryService = plantSubcategoryService;
        this.plantSubcategoryRepository = plantSubcategoryRepository;
    }

    /**
     * {@code POST  /plant-subcategories} : Create a new plantSubcategory.
     *
     * @param plantSubcategory the plantSubcategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plantSubcategory, or with status {@code 400 (Bad Request)} if the plantSubcategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PlantSubcategory> createPlantSubcategory(@RequestBody PlantSubcategory plantSubcategory)
        throws URISyntaxException {
        LOG.debug("REST request to save PlantSubcategory : {}", plantSubcategory);
        if (plantSubcategory.getId() != null) {
            throw new BadRequestAlertException("A new plantSubcategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        plantSubcategory = plantSubcategoryService.save(plantSubcategory);
        return ResponseEntity.created(new URI("/api/plant-subcategories/" + plantSubcategory.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, plantSubcategory.getId().toString()))
            .body(plantSubcategory);
    }

    /**
     * {@code PUT  /plant-subcategories/:id} : Updates an existing plantSubcategory.
     *
     * @param id the id of the plantSubcategory to save.
     * @param plantSubcategory the plantSubcategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plantSubcategory,
     * or with status {@code 400 (Bad Request)} if the plantSubcategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plantSubcategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlantSubcategory> updatePlantSubcategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlantSubcategory plantSubcategory
    ) throws URISyntaxException {
        LOG.debug("REST request to update PlantSubcategory : {}, {}", id, plantSubcategory);
        if (plantSubcategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plantSubcategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plantSubcategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        plantSubcategory = plantSubcategoryService.update(plantSubcategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plantSubcategory.getId().toString()))
            .body(plantSubcategory);
    }

    /**
     * {@code PATCH  /plant-subcategories/:id} : Partial updates given fields of an existing plantSubcategory, field will ignore if it is null
     *
     * @param id the id of the plantSubcategory to save.
     * @param plantSubcategory the plantSubcategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plantSubcategory,
     * or with status {@code 400 (Bad Request)} if the plantSubcategory is not valid,
     * or with status {@code 404 (Not Found)} if the plantSubcategory is not found,
     * or with status {@code 500 (Internal Server Error)} if the plantSubcategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlantSubcategory> partialUpdatePlantSubcategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlantSubcategory plantSubcategory
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PlantSubcategory partially : {}, {}", id, plantSubcategory);
        if (plantSubcategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plantSubcategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plantSubcategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlantSubcategory> result = plantSubcategoryService.partialUpdate(plantSubcategory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plantSubcategory.getId().toString())
        );
    }

    /**
     * {@code GET  /plant-subcategories} : get all the plantSubcategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plantSubcategories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PlantSubcategory>> getAllPlantSubcategories(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of PlantSubcategories");
        Page<PlantSubcategory> page = plantSubcategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plant-subcategories/:id} : get the "id" plantSubcategory.
     *
     * @param id the id of the plantSubcategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plantSubcategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlantSubcategory> getPlantSubcategory(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PlantSubcategory : {}", id);
        Optional<PlantSubcategory> plantSubcategory = plantSubcategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plantSubcategory);
    }

    /**
     * {@code DELETE  /plant-subcategories/:id} : delete the "id" plantSubcategory.
     *
     * @param id the id of the plantSubcategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlantSubcategory(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PlantSubcategory : {}", id);
        plantSubcategoryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
