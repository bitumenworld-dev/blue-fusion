package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.domain.Workshop;
import com.bitumen.bluefusion.repository.WorkshopRepository;
import com.bitumen.bluefusion.service.WorkshopService;
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
 * REST controller for managing {@link com.bitumen.bluefusion.domain.Workshop}.
 */
@RestController
@RequestMapping("/api/workshops")
public class WorkshopResource {

    private static final Logger LOG = LoggerFactory.getLogger(WorkshopResource.class);

    private static final String ENTITY_NAME = "workshop";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkshopService workshopService;

    private final WorkshopRepository workshopRepository;

    public WorkshopResource(WorkshopService workshopService, WorkshopRepository workshopRepository) {
        this.workshopService = workshopService;
        this.workshopRepository = workshopRepository;
    }

    /**
     * {@code POST  /workshops} : Create a new workshop.
     *
     * @param workshop the workshop to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workshop, or with status {@code 400 (Bad Request)} if the workshop has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Workshop> createWorkshop(@RequestBody Workshop workshop) throws URISyntaxException {
        LOG.debug("REST request to save Workshop : {}", workshop);
        if (workshop.getId() != null) {
            throw new BadRequestAlertException("A new workshop cannot already have an ID", ENTITY_NAME, "idexists");
        }
        workshop = workshopService.save(workshop);
        return ResponseEntity.created(new URI("/api/workshops/" + workshop.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, workshop.getId().toString()))
            .body(workshop);
    }

    /**
     * {@code PUT  /workshops/:id} : Updates an existing workshop.
     *
     * @param id the id of the workshop to save.
     * @param workshop the workshop to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workshop,
     * or with status {@code 400 (Bad Request)} if the workshop is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workshop couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Workshop> updateWorkshop(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Workshop workshop
    ) throws URISyntaxException {
        LOG.debug("REST request to update Workshop : {}, {}", id, workshop);
        if (workshop.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workshop.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workshopRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        workshop = workshopService.update(workshop);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workshop.getId().toString()))
            .body(workshop);
    }

    /**
     * {@code PATCH  /workshops/:id} : Partial updates given fields of an existing workshop, field will ignore if it is null
     *
     * @param id the id of the workshop to save.
     * @param workshop the workshop to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workshop,
     * or with status {@code 400 (Bad Request)} if the workshop is not valid,
     * or with status {@code 404 (Not Found)} if the workshop is not found,
     * or with status {@code 500 (Internal Server Error)} if the workshop couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Workshop> partialUpdateWorkshop(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Workshop workshop
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Workshop partially : {}, {}", id, workshop);
        if (workshop.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workshop.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workshopRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Workshop> result = workshopService.partialUpdate(workshop);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workshop.getId().toString())
        );
    }

    /**
     * {@code GET  /workshops} : get all the workshops.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workshops in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Workshop>> getAllWorkshops(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Workshops");
        Page<Workshop> page = workshopService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /workshops/:id} : get the "id" workshop.
     *
     * @param id the id of the workshop to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workshop, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Workshop> getWorkshop(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Workshop : {}", id);
        Optional<Workshop> workshop = workshopService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workshop);
    }

    /**
     * {@code DELETE  /workshops/:id} : delete the "id" workshop.
     *
     * @param id the id of the workshop to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkshop(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Workshop : {}", id);
        workshopService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
