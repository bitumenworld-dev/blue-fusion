package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.domain.AssetPlantServiceReading;
import com.bitumen.bluefusion.service.AssetPlantServiceReadingService;
import com.bitumen.bluefusion.service.assetPlantServiceReading.dto.AssetPlantServiceReadingMapper;
import com.bitumen.bluefusion.service.assetPlantServiceReading.dto.AssetPlantServiceReadingRequest;
import com.bitumen.bluefusion.service.assetPlantServiceReading.dto.AssetPlantServiceReadingResponse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/asset-plant-service-readings")
public final class AssetPlantServiceReadingResource {

    private static final Logger LOG = LoggerFactory.getLogger(AssetPlantServiceReadingResource.class);

    private static final String ENTITY_NAME = "assetPlantServiceReading";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssetPlantServiceReadingService assetPlantServiceReadingService;

    @PostMapping("")
    public ResponseEntity<AssetPlantServiceReadingResponse> createAssetPlantServiceReading(
        @RequestBody AssetPlantServiceReadingRequest req
    ) throws URISyntaxException {
        AssetPlantServiceReading entity = AssetPlantServiceReading.builder()
            .assetPlantId(req.assetPlantId())
            .nextServiceSmrReading(req.nextServiceSmrReading())
            .estimatedUnitsPerDay(req.estimatedUnitsPerDay())
            .latestSmrReadings(req.latestSmrReadings())
            .serviceInterval(req.serviceInterval())
            .lastServiceDate(req.lastServiceDate())
            .latestSmrDate(req.latestSmrDate())
            .lastServiceSmr(req.lastServiceSmr())
            .serviceUnit(req.serviceUnit())
            .estimatedNextServiceDate(req.estimatedNextServiceDate())
            .build();
        AssetPlantServiceReading saved = assetPlantServiceReadingService.save(entity);
        AssetPlantServiceReadingResponse response = AssetPlantServiceReadingMapper.map.apply(saved);
        return ResponseEntity.created(new URI("/api/asset-plant-service-readings/" + response.assetPlantServiceReadingId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, String.valueOf(response.assetPlantId())))
            .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetPlantServiceReadingResponse> updateAssetPlantServiceReading(
        @RequestBody AssetPlantServiceReadingRequest req
    ) {
        AssetPlantServiceReading entity = AssetPlantServiceReading.builder()
            .assetPlantId(req.assetPlantId())
            .nextServiceSmrReading(req.nextServiceSmrReading())
            .estimatedUnitsPerDay(req.estimatedUnitsPerDay())
            .latestSmrReadings(req.latestSmrReadings())
            .serviceInterval(req.serviceInterval())
            .lastServiceDate(req.lastServiceDate())
            .latestSmrDate(req.latestSmrDate())
            .lastServiceSmr(req.lastServiceSmr())
            .serviceUnit(req.serviceUnit())
            .estimatedNextServiceDate(req.estimatedNextServiceDate())
            .build();
        AssetPlantServiceReading updated = assetPlantServiceReadingService.update(entity);
        AssetPlantServiceReadingResponse response = AssetPlantServiceReadingMapper.map.apply(updated);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    String.valueOf(response.assetPlantServiceReadingId())
                )
            )
            .body(response);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssetPlantServiceReadingResponse> partialUpdateAssetPlantServiceReading(
        @RequestBody AssetPlantServiceReadingRequest req
    ) {
        AssetPlantServiceReading entity = AssetPlantServiceReading.builder()
            .assetPlantId(req.assetPlantId())
            .nextServiceSmrReading(req.nextServiceSmrReading())
            .estimatedUnitsPerDay(req.estimatedUnitsPerDay())
            .latestSmrReadings(req.latestSmrReadings())
            .serviceInterval(req.serviceInterval())
            .lastServiceDate(req.lastServiceDate())
            .latestSmrDate(req.latestSmrDate())
            .lastServiceSmr(req.lastServiceSmr())
            .serviceUnit(req.serviceUnit())
            .estimatedNextServiceDate(req.estimatedNextServiceDate())
            .build();
        Optional<AssetPlantServiceReading> updated = assetPlantServiceReadingService.partialUpdate(entity);
        AssetPlantServiceReadingResponse response = updated.map(AssetPlantServiceReadingMapper.map).orElse(null);
        return ResponseUtil.wrapOrNotFound(
            Optional.ofNullable(response),
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                true,
                ENTITY_NAME,
                response != null ? String.valueOf(response.assetPlantServiceReadingId()) : null
            )
        );
    }

    @GetMapping("")
    public ResponseEntity<List<AssetPlantServiceReadingResponse>> getAllAssetPlantServiceReadings(
        @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
        @RequestParam(value = "size", defaultValue = "100", required = false) Integer size,
        @RequestParam(required = false) Long assetPlantServiceReadingId,
        @RequestParam(required = false) Long assetPlantId,
        @RequestParam(required = false) Boolean isActive
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("assetPlantServiceReadingId").descending());
        Page<AssetPlantServiceReadingResponse> pageAssetPlantServiceReadings = assetPlantServiceReadingService
            .findAll(pageable, assetPlantServiceReadingId, assetPlantId, isActive)
            .map(AssetPlantServiceReadingMapper.map);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(),
            pageAssetPlantServiceReadings
        );
        return ResponseEntity.ok().headers(headers).body(pageAssetPlantServiceReadings.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetPlantServiceReadingResponse> getAssetPlantServiceReading(
        @PathVariable("id") Long assetPlantServiceReadingId
    ) {
        Optional<AssetPlantServiceReadingResponse> response = assetPlantServiceReadingService
            .findOne(assetPlantServiceReadingId)
            .map(AssetPlantServiceReadingMapper.map);
        return ResponseUtil.wrapOrNotFound(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssetPlantServiceReading(@PathVariable("id") Long assetPlantServiceReadingId) {
        LOG.debug("REST request to delete AssetPlantServiceReading : {}", assetPlantServiceReadingId);
        assetPlantServiceReadingService.delete(assetPlantServiceReadingId);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, String.valueOf(assetPlantServiceReadingId)))
            .build();
    }
}
