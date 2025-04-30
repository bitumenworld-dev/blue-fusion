package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.domain.AssetPlantServiceReading;
import com.bitumen.bluefusion.repository.AssetPlantServiceReadingRepository;
import com.bitumen.bluefusion.service.AssetPlantServiceReadingService;
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
 * REST controller for managing {@link com.bitumen.bluefusion.domain.AssetPlantServiceReading}.
 */
@RestController
@RequestMapping("/api/asset-plant-service-readings")
public class AssetPlantServiceReadingResource {

    private static final Logger LOG = LoggerFactory.getLogger(AssetPlantServiceReadingResource.class);

    private static final String ENTITY_NAME = "assetPlantServiceReading";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssetPlantServiceReadingService assetPlantServiceReadingService;

    private final AssetPlantServiceReadingRepository assetPlantServiceReadingRepository;

    public AssetPlantServiceReadingResource(
        AssetPlantServiceReadingService assetPlantServiceReadingService,
        AssetPlantServiceReadingRepository assetPlantServiceReadingRepository
    ) {
        this.assetPlantServiceReadingService = assetPlantServiceReadingService;
        this.assetPlantServiceReadingRepository = assetPlantServiceReadingRepository;
    }

    /**
     * {@code POST  /asset-plant-service-readings} : Create a new assetPlantServiceReading.
     *
     * @param assetPlantServiceReading the assetPlantServiceReading to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assetPlantServiceReading, or with status {@code 400 (Bad Request)} if the assetPlantServiceReading has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AssetPlantServiceReading> createAssetPlantServiceReading(
        @RequestBody AssetPlantServiceReading assetPlantServiceReading
    ) throws URISyntaxException {
        LOG.debug("REST request to save AssetPlantServiceReading : {}", assetPlantServiceReading);
        if (assetPlantServiceReading.getId() != null) {
            throw new BadRequestAlertException("A new assetPlantServiceReading cannot already have an ID", ENTITY_NAME, "idexists");
        }
        assetPlantServiceReading = assetPlantServiceReadingService.save(assetPlantServiceReading);
        return ResponseEntity.created(new URI("/api/asset-plant-service-readings/" + assetPlantServiceReading.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, assetPlantServiceReading.getId().toString()))
            .body(assetPlantServiceReading);
    }

    /**
     * {@code PUT  /asset-plant-service-readings/:id} : Updates an existing assetPlantServiceReading.
     *
     * @param id the id of the assetPlantServiceReading to save.
     * @param assetPlantServiceReading the assetPlantServiceReading to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetPlantServiceReading,
     * or with status {@code 400 (Bad Request)} if the assetPlantServiceReading is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assetPlantServiceReading couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AssetPlantServiceReading> updateAssetPlantServiceReading(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AssetPlantServiceReading assetPlantServiceReading
    ) throws URISyntaxException {
        LOG.debug("REST request to update AssetPlantServiceReading : {}, {}", id, assetPlantServiceReading);
        if (assetPlantServiceReading.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assetPlantServiceReading.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assetPlantServiceReadingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        assetPlantServiceReading = assetPlantServiceReadingService.update(assetPlantServiceReading);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assetPlantServiceReading.getId().toString()))
            .body(assetPlantServiceReading);
    }

    /**
     * {@code PATCH  /asset-plant-service-readings/:id} : Partial updates given fields of an existing assetPlantServiceReading, field will ignore if it is null
     *
     * @param id the id of the assetPlantServiceReading to save.
     * @param assetPlantServiceReading the assetPlantServiceReading to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetPlantServiceReading,
     * or with status {@code 400 (Bad Request)} if the assetPlantServiceReading is not valid,
     * or with status {@code 404 (Not Found)} if the assetPlantServiceReading is not found,
     * or with status {@code 500 (Internal Server Error)} if the assetPlantServiceReading couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssetPlantServiceReading> partialUpdateAssetPlantServiceReading(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AssetPlantServiceReading assetPlantServiceReading
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update AssetPlantServiceReading partially : {}, {}", id, assetPlantServiceReading);
        if (assetPlantServiceReading.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assetPlantServiceReading.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assetPlantServiceReadingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssetPlantServiceReading> result = assetPlantServiceReadingService.partialUpdate(assetPlantServiceReading);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assetPlantServiceReading.getId().toString())
        );
    }

    /**
     * {@code GET  /asset-plant-service-readings} : get all the assetPlantServiceReadings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assetPlantServiceReadings in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AssetPlantServiceReading>> getAllAssetPlantServiceReadings(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of AssetPlantServiceReadings");
        Page<AssetPlantServiceReading> page = assetPlantServiceReadingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /asset-plant-service-readings/:id} : get the "id" assetPlantServiceReading.
     *
     * @param id the id of the assetPlantServiceReading to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assetPlantServiceReading, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AssetPlantServiceReading> getAssetPlantServiceReading(@PathVariable("id") Long id) {
        LOG.debug("REST request to get AssetPlantServiceReading : {}", id);
        Optional<AssetPlantServiceReading> assetPlantServiceReading = assetPlantServiceReadingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assetPlantServiceReading);
    }

    /**
     * {@code DELETE  /asset-plant-service-readings/:id} : delete the "id" assetPlantServiceReading.
     *
     * @param id the id of the assetPlantServiceReading to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssetPlantServiceReading(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete AssetPlantServiceReading : {}", id);
        assetPlantServiceReadingService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
