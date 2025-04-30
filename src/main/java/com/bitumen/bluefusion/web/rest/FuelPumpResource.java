package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.domain.FuelPump;
import com.bitumen.bluefusion.repository.FuelPumpRepository;
import com.bitumen.bluefusion.service.FuelPumpService;
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
 * REST controller for managing {@link com.bitumen.bluefusion.domain.FuelPump}.
 */
@RestController
@RequestMapping("/api/fuel-pumps")
public class FuelPumpResource {

    private static final Logger LOG = LoggerFactory.getLogger(FuelPumpResource.class);

    private static final String ENTITY_NAME = "fuelPump";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuelPumpService fuelPumpService;

    private final FuelPumpRepository fuelPumpRepository;

    public FuelPumpResource(FuelPumpService fuelPumpService, FuelPumpRepository fuelPumpRepository) {
        this.fuelPumpService = fuelPumpService;
        this.fuelPumpRepository = fuelPumpRepository;
    }

    /**
     * {@code POST  /fuel-pumps} : Create a new fuelPump.
     *
     * @param fuelPump the fuelPump to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fuelPump, or with status {@code 400 (Bad Request)} if the fuelPump has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FuelPump> createFuelPump(@RequestBody FuelPump fuelPump) throws URISyntaxException {
        LOG.debug("REST request to save FuelPump : {}", fuelPump);
        if (fuelPump.getId() != null) {
            throw new BadRequestAlertException("A new fuelPump cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fuelPump = fuelPumpService.save(fuelPump);
        return ResponseEntity.created(new URI("/api/fuel-pumps/" + fuelPump.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, fuelPump.getId().toString()))
            .body(fuelPump);
    }

    /**
     * {@code PUT  /fuel-pumps/:id} : Updates an existing fuelPump.
     *
     * @param id the id of the fuelPump to save.
     * @param fuelPump the fuelPump to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelPump,
     * or with status {@code 400 (Bad Request)} if the fuelPump is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fuelPump couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FuelPump> updateFuelPump(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelPump fuelPump
    ) throws URISyntaxException {
        LOG.debug("REST request to update FuelPump : {}, {}", id, fuelPump);
        if (fuelPump.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelPump.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelPumpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fuelPump = fuelPumpService.update(fuelPump);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fuelPump.getId().toString()))
            .body(fuelPump);
    }

    /**
     * {@code PATCH  /fuel-pumps/:id} : Partial updates given fields of an existing fuelPump, field will ignore if it is null
     *
     * @param id the id of the fuelPump to save.
     * @param fuelPump the fuelPump to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelPump,
     * or with status {@code 400 (Bad Request)} if the fuelPump is not valid,
     * or with status {@code 404 (Not Found)} if the fuelPump is not found,
     * or with status {@code 500 (Internal Server Error)} if the fuelPump couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FuelPump> partialUpdateFuelPump(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelPump fuelPump
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FuelPump partially : {}, {}", id, fuelPump);
        if (fuelPump.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelPump.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelPumpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FuelPump> result = fuelPumpService.partialUpdate(fuelPump);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fuelPump.getId().toString())
        );
    }

    /**
     * {@code GET  /fuel-pumps} : get all the fuelPumps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fuelPumps in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FuelPump>> getAllFuelPumps(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of FuelPumps");
        Page<FuelPump> page = fuelPumpService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fuel-pumps/:id} : get the "id" fuelPump.
     *
     * @param id the id of the fuelPump to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fuelPump, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FuelPump> getFuelPump(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FuelPump : {}", id);
        Optional<FuelPump> fuelPump = fuelPumpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fuelPump);
    }

    /**
     * {@code DELETE  /fuel-pumps/:id} : delete the "id" fuelPump.
     *
     * @param id the id of the fuelPump to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuelPump(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FuelPump : {}", id);
        fuelPumpService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
