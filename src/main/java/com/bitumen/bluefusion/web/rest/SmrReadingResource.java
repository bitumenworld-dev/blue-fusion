package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.service.smrReadingService.SmrReadingService;
import com.bitumen.bluefusion.service.smrReadingService.dto.SmrReadingRequest;
import com.bitumen.bluefusion.service.smrReadingService.dto.SmrReadingResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping
public class SmrReadingResource {

    private static final String ENTITY_NAME = "smrReading";

    @Value("blueFusionApp")
    private String applicationName;

    private final SmrReadingService smrReadingService;

    @PostMapping
    public ResponseEntity<SmrReadingResponse> createSmrReading(@RequestBody SmrReadingRequest smrReadingRequest) throws URISyntaxException {
        SmrReadingResponse smrReading = smrReadingService.save(smrReadingRequest);
        return ResponseEntity.created(new URI("/api/smr-reading" + smrReading.smrReadingId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, String.valueOf(smrReading.smrReadingId())))
            .body(smrReading);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SmrReadingResponse> updateSmrReading(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SmrReadingRequest smrReadingRequest
    ) {
        SmrReadingResponse smrReading = smrReadingService.update(id, smrReadingRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, String.valueOf(smrReading.smrReadingId())))
            .body(smrReading);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SmrReadingResponse> partialUpdateSmrReading(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SmrReadingRequest smrReadingRequest
    ) {
        SmrReadingResponse smrReading = smrReadingService.partialUpdate(id, smrReadingRequest);
        return ResponseUtil.wrapOrNotFound(
            Optional.of(smrReading),
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, String.valueOf(smrReading.smrReadingId()))
        );
    }

    @GetMapping("")
    public ResponseEntity<List<SmrReadingResponse>> getAllSmrReading(
        @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
        @RequestParam(value = "size", defaultValue = "100", required = false) Integer size,
        @RequestParam(required = false) Long smrReadingId,
        @RequestParam(required = false) String unit,
        @RequestParam(required = false) Float smrReadingValue
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("assetPlant").descending());
        Page<SmrReadingResponse> smrReadingPage = smrReadingService.findAll(pageable, smrReadingId, unit, smrReadingValue);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(),
            smrReadingPage
        );
        return ResponseEntity.ok().headers(headers).body(smrReadingPage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SmrReadingResponse> getSmrReading(
        @PathVariable("id") Long smrReadingId,
        @RequestBody SmrReadingRequest smrReadingRequest
    ) {
        return ResponseEntity.ok().body(smrReadingService.findOne(smrReadingId, smrReadingRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSmrReading(@PathVariable("id") Long smrReadingId) {
        smrReadingService.delete(smrReadingId);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, String.valueOf(smrReadingId)))
            .build();
    }
}
