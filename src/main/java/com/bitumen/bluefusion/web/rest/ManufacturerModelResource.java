package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.domain.ManufacturerModel;
import com.bitumen.bluefusion.repository.ManufacturerModelRepository;
import com.bitumen.bluefusion.service.ManufacturerModelService;
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
 * REST controller for managing {@link com.bitumen.bluefusion.domain.ManufacturerModel}.
 */
@RestController
@RequestMapping("/api/manufacturer-models")
public class ManufacturerModelResource {

    private static final Logger LOG = LoggerFactory.getLogger(ManufacturerModelResource.class);

    private static final String ENTITY_NAME = "manufacturerModel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ManufacturerModelService manufacturerModelService;

    private final ManufacturerModelRepository manufacturerModelRepository;

    public ManufacturerModelResource(
        ManufacturerModelService manufacturerModelService,
        ManufacturerModelRepository manufacturerModelRepository
    ) {
        this.manufacturerModelService = manufacturerModelService;
        this.manufacturerModelRepository = manufacturerModelRepository;
    }

    /**
     * {@code POST  /manufacturer-models} : Create a new manufacturerModel.
     *
     * @param manufacturerModel the manufacturerModel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new manufacturerModel, or with status {@code 400 (Bad Request)} if the manufacturerModel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ManufacturerModel> createManufacturerModel(@RequestBody ManufacturerModel manufacturerModel)
        throws URISyntaxException {
        LOG.debug("REST request to save ManufacturerModel : {}", manufacturerModel);
        if (manufacturerModel.getId() != null) {
            throw new BadRequestAlertException("A new manufacturerModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        manufacturerModel = manufacturerModelService.save(manufacturerModel);
        return ResponseEntity.created(new URI("/api/manufacturer-models/" + manufacturerModel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, manufacturerModel.getId().toString()))
            .body(manufacturerModel);
    }

    /**
     * {@code PUT  /manufacturer-models/:id} : Updates an existing manufacturerModel.
     *
     * @param id the id of the manufacturerModel to save.
     * @param manufacturerModel the manufacturerModel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated manufacturerModel,
     * or with status {@code 400 (Bad Request)} if the manufacturerModel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the manufacturerModel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ManufacturerModel> updateManufacturerModel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ManufacturerModel manufacturerModel
    ) throws URISyntaxException {
        LOG.debug("REST request to update ManufacturerModel : {}, {}", id, manufacturerModel);
        if (manufacturerModel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, manufacturerModel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!manufacturerModelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        manufacturerModel = manufacturerModelService.update(manufacturerModel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, manufacturerModel.getId().toString()))
            .body(manufacturerModel);
    }

    /**
     * {@code PATCH  /manufacturer-models/:id} : Partial updates given fields of an existing manufacturerModel, field will ignore if it is null
     *
     * @param id the id of the manufacturerModel to save.
     * @param manufacturerModel the manufacturerModel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated manufacturerModel,
     * or with status {@code 400 (Bad Request)} if the manufacturerModel is not valid,
     * or with status {@code 404 (Not Found)} if the manufacturerModel is not found,
     * or with status {@code 500 (Internal Server Error)} if the manufacturerModel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ManufacturerModel> partialUpdateManufacturerModel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ManufacturerModel manufacturerModel
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ManufacturerModel partially : {}, {}", id, manufacturerModel);
        if (manufacturerModel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, manufacturerModel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!manufacturerModelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ManufacturerModel> result = manufacturerModelService.partialUpdate(manufacturerModel);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, manufacturerModel.getId().toString())
        );
    }

    /**
     * {@code GET  /manufacturer-models} : get all the manufacturerModels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of manufacturerModels in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ManufacturerModel>> getAllManufacturerModels(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of ManufacturerModels");
        Page<ManufacturerModel> page = manufacturerModelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /manufacturer-models/:id} : get the "id" manufacturerModel.
     *
     * @param id the id of the manufacturerModel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the manufacturerModel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerModel> getManufacturerModel(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ManufacturerModel : {}", id);
        Optional<ManufacturerModel> manufacturerModel = manufacturerModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(manufacturerModel);
    }

    /**
     * {@code DELETE  /manufacturer-models/:id} : delete the "id" manufacturerModel.
     *
     * @param id the id of the manufacturerModel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturerModel(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ManufacturerModel : {}", id);
        manufacturerModelService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
