package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.domain.Manufacturer;
import com.bitumen.bluefusion.repository.ManufacturerRepository;
import com.bitumen.bluefusion.service.ManufacturerService;
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
 * REST controller for managing {@link com.bitumen.bluefusion.domain.Manufacturer}.
 */
@RestController
@RequestMapping("/api/manufacturers")
public class ManufacturerResource {

    private static final Logger LOG = LoggerFactory.getLogger(ManufacturerResource.class);

    private static final String ENTITY_NAME = "manufacturer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ManufacturerService manufacturerService;

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerResource(ManufacturerService manufacturerService, ManufacturerRepository manufacturerRepository) {
        this.manufacturerService = manufacturerService;
        this.manufacturerRepository = manufacturerRepository;
    }

    /**
     * {@code POST  /manufacturers} : Create a new manufacturer.
     *
     * @param manufacturer the manufacturer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new manufacturer, or with status {@code 400 (Bad Request)} if the manufacturer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Manufacturer> createManufacturer(@RequestBody Manufacturer manufacturer) throws URISyntaxException {
        LOG.debug("REST request to save Manufacturer : {}", manufacturer);
        if (manufacturer.getId() != null) {
            throw new BadRequestAlertException("A new manufacturer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        manufacturer = manufacturerService.save(manufacturer);
        return ResponseEntity.created(new URI("/api/manufacturers/" + manufacturer.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, manufacturer.getId().toString()))
            .body(manufacturer);
    }

    /**
     * {@code PUT  /manufacturers/:id} : Updates an existing manufacturer.
     *
     * @param id the id of the manufacturer to save.
     * @param manufacturer the manufacturer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated manufacturer,
     * or with status {@code 400 (Bad Request)} if the manufacturer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the manufacturer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Manufacturer> updateManufacturer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Manufacturer manufacturer
    ) throws URISyntaxException {
        LOG.debug("REST request to update Manufacturer : {}, {}", id, manufacturer);
        if (manufacturer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, manufacturer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!manufacturerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        manufacturer = manufacturerService.update(manufacturer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, manufacturer.getId().toString()))
            .body(manufacturer);
    }

    /**
     * {@code PATCH  /manufacturers/:id} : Partial updates given fields of an existing manufacturer, field will ignore if it is null
     *
     * @param id the id of the manufacturer to save.
     * @param manufacturer the manufacturer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated manufacturer,
     * or with status {@code 400 (Bad Request)} if the manufacturer is not valid,
     * or with status {@code 404 (Not Found)} if the manufacturer is not found,
     * or with status {@code 500 (Internal Server Error)} if the manufacturer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Manufacturer> partialUpdateManufacturer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Manufacturer manufacturer
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Manufacturer partially : {}, {}", id, manufacturer);
        if (manufacturer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, manufacturer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!manufacturerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Manufacturer> result = manufacturerService.partialUpdate(manufacturer);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, manufacturer.getId().toString())
        );
    }

    /**
     * {@code GET  /manufacturers} : get all the manufacturers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of manufacturers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Manufacturer>> getAllManufacturers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Manufacturers");
        Page<Manufacturer> page = manufacturerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /manufacturers/:id} : get the "id" manufacturer.
     *
     * @param id the id of the manufacturer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the manufacturer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Manufacturer> getManufacturer(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Manufacturer : {}", id);
        Optional<Manufacturer> manufacturer = manufacturerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(manufacturer);
    }

    /**
     * {@code DELETE  /manufacturers/:id} : delete the "id" manufacturer.
     *
     * @param id the id of the manufacturer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Manufacturer : {}", id);
        manufacturerService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
