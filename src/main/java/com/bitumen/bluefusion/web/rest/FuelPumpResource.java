package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.service.fuelPump.FuelPumpService;
import com.bitumen.bluefusion.service.fuelPump.dto.FuelPumpRequest;
import com.bitumen.bluefusion.service.fuelPump.dto.FuelPumpResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fuel-pumps")
public class FuelPumpResource {

    private static final String ENTITY_NAME = "fuelPump";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuelPumpService fuelPumpService;

    @PostMapping("")
    public ResponseEntity<FuelPumpResponse> createFuelPump(@RequestBody FuelPumpRequest fuelPumpRequest) throws URISyntaxException {
        FuelPumpResponse fuelPump = fuelPumpService.save(fuelPumpRequest);
        return ResponseEntity.created(new URI("/api/fuel-pumps/" + fuelPump.id()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, fuelPump.id().toString()))
            .body(fuelPump);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuelPumpResponse> updateFuelPump(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelPumpRequest fuelPumpRequest
    ) throws URISyntaxException {
        FuelPumpResponse fuelPump = fuelPumpService.update(id, fuelPumpRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fuelPump.id().toString()))
            .body(fuelPump);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FuelPumpResponse> partialUpdateFuelPump(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelPumpRequest fuelPumpRequest
    ) throws URISyntaxException {
        FuelPumpResponse result = fuelPumpService.partialUpdate(id, fuelPumpRequest);

        return ResponseUtil.wrapOrNotFound(
            Optional.of(result),
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<FuelPumpResponse>> getAllFuelPumps(
        @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
        @RequestParam(value = "size", defaultValue = "100", required = false) Integer size,
        @RequestParam(value = "storageId", required = false) Long storageId,
        @RequestParam(value = "isActive", required = false) Boolean isActive,
        @RequestParam(value = "fuelPumpCode", required = false) String fuelPumpCode
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fuelPumpId").descending());
        Page<FuelPumpResponse> fuelPumps = fuelPumpService.findAll(pageable, storageId, isActive, fuelPumpCode);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), fuelPumps);

        return ResponseEntity.ok().headers(headers).body(fuelPumps.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuelPumpResponse> getFuelPump(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(fuelPumpService.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuelPump(@PathVariable("id") Long id) {
        fuelPumpService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
