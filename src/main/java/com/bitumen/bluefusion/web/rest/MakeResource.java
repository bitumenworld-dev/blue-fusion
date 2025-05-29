package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.domain.Make;
import com.bitumen.bluefusion.repository.MakeRepository;
import com.bitumen.bluefusion.service.makeService.MakeService;
import com.bitumen.bluefusion.service.makeService.dto.MakeRequest;
import com.bitumen.bluefusion.service.makeService.dto.MakeResponse;
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

/**
 * REST controller for managing {@link Make}.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/make")
public class MakeResource {

    private static final Logger LOG = LoggerFactory.getLogger(MakeResource.class);

    private static final String ENTITY_NAME = "manufacturer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MakeService makeService;

    @PostMapping("")
    public ResponseEntity<MakeResponse> createManufacturer(@RequestBody MakeRequest make) throws URISyntaxException {
        MakeResponse makeResponse = makeService.save(make);
        return ResponseEntity.created(new URI("/api/manufacturers/" + makeResponse.id()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, makeResponse.id().toString()))
            .body(makeResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MakeResponse> updateManufacturer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MakeRequest makeRequest
    ) {
        MakeResponse make = makeService.update(id, makeRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, make.id().toString()))
            .body(make);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MakeResponse> partialUpdateManufacturer(
        @PathVariable(value = "id") final Long id,
        @RequestBody MakeRequest makeRequest
    ) throws URISyntaxException {
        MakeResponse result = makeService.partialUpdate(id, makeRequest);

        return ResponseUtil.wrapOrNotFound(
            Optional.ofNullable(result),
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.id().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<MakeResponse>> getAllManufacturers(
        @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
        @RequestParam(value = "size", defaultValue = "100", required = false) Integer size,
        @RequestParam(value = "make", required = false) String make
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("makeId").descending());
        Page<MakeResponse> makeResponsePage = makeService.findAll(pageable, make);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(),
            makeResponsePage
        );

        return ResponseEntity.ok().headers(headers).body(makeResponsePage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MakeResponse> getManufacturer(@PathVariable("id") Long id) {
        MakeResponse make = makeService.findOne(id);
        return new ResponseEntity<>(make, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable("id") Long id) {
        makeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
