package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.domain.AssetPlant;
import com.bitumen.bluefusion.repository.AssetPlantRepository;
import com.bitumen.bluefusion.service.assetPlant.AssetPlantService;
import com.bitumen.bluefusion.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.bitumen.bluefusion.domain.AssetPlant}.
 */
@RestController
@RequestMapping("/api/asset-plants")
public class AssetPlantResource {

    private static final Logger LOG = LoggerFactory.getLogger(AssetPlantResource.class);

    private static final String ENTITY_NAME = "assetPlant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssetPlantService assetPlantService;

    private final AssetPlantRepository assetPlantRepository;

    public AssetPlantResource(AssetPlantService assetPlantService, AssetPlantRepository assetPlantRepository) {
        this.assetPlantService = assetPlantService;
        this.assetPlantRepository = assetPlantRepository;
    }

    /**
     * {@code POST  /asset-plants} : Create a new assetPlant.
     *
     * @param assetPlant the assetPlant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assetPlant, or with status {@code 400 (Bad Request)} if the assetPlant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AssetPlant> createAssetPlant(@Valid @RequestBody AssetPlant assetPlant) throws URISyntaxException {
        LOG.debug("REST request to save AssetPlant : {}", assetPlant);
        if (assetPlant.getAssetPlantId() != null) {
            throw new BadRequestAlertException("A new assetPlant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        assetPlant = assetPlantService.save(assetPlant);
        return ResponseEntity.created(new URI("/api/asset-plants/" + assetPlant.getAssetPlantId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, assetPlant.getAssetPlantId().toString()))
            .body(assetPlant);
    }

    /**
     * {@code PUT  /asset-plants/:id} : Updates an existing assetPlant.
     *
     * @param id the id of the assetPlant to save.
     * @param assetPlant the assetPlant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetPlant,
     * or with status {@code 400 (Bad Request)} if the assetPlant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assetPlant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AssetPlant> updateAssetPlant(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AssetPlant assetPlant
    ) throws URISyntaxException {
        LOG.debug("REST request to update AssetPlant : {}, {}", id, assetPlant);
        if (assetPlant.getAssetPlantId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assetPlant.getAssetPlantId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assetPlantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        assetPlant = assetPlantService.update(assetPlant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assetPlant.getAssetPlantId().toString()))
            .body(assetPlant);
    }

    /**
     * {@code PATCH  /asset-plants/:id} : Partial updates given fields of an existing assetPlant, field will ignore if it is null
     *
     * @param id the id of the assetPlant to save.
     * @param assetPlant the assetPlant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetPlant,
     * or with status {@code 400 (Bad Request)} if the assetPlant is not valid,
     * or with status {@code 404 (Not Found)} if the assetPlant is not found,
     * or with status {@code 500 (Internal Server Error)} if the assetPlant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssetPlant> partialUpdateAssetPlant(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AssetPlant assetPlant
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update AssetPlant partially : {}, {}", id, assetPlant);
        if (assetPlant.getAssetPlantId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assetPlant.getAssetPlantId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assetPlantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssetPlant> result = assetPlantService.partialUpdate(assetPlant);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assetPlant.getAssetPlantId().toString())
        );
    }

    /**
     * {@code GET  /asset-plants} : get all the assetPlants.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assetPlants in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AssetPlant>> getAllAssetPlants(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of AssetPlants");
        Page<AssetPlant> page = assetPlantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /asset-plants/:id} : get the "id" assetPlant.
     *
     * @param id the id of the assetPlant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assetPlant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AssetPlant> getAssetPlant(@PathVariable("id") Long id) {
        LOG.debug("REST request to get AssetPlant : {}", id);
        Optional<AssetPlant> assetPlant = assetPlantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assetPlant);
    }

    /**
     * {@code DELETE  /asset-plants/:id} : delete the "id" assetPlant.
     *
     * @param id the id of the assetPlant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssetPlant(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete AssetPlant : {}", id);
        assetPlantService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
