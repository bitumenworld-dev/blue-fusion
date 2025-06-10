package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.service.assetPlantServiceReading.AssetPlantServiceReadingService;
import com.bitumen.bluefusion.service.assetPlantServiceReading.dto.AssetPlantServiceReadingRequest;
import com.bitumen.bluefusion.service.assetPlantServiceReading.dto.AssetPlantServiceReadingResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/asset-plant-service-readings")
public class AssetPlantServiceReadingResource {

    private static final String ENTITY_NAME = "assetPlantServiceReading";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssetPlantServiceReadingService assetPlantServiceReadingService;

    @PostMapping("")
    public ResponseEntity<AssetPlantServiceReadingResponse> createAssetPlantServiceReading(
        @RequestBody AssetPlantServiceReadingRequest assetPlantServiceReadingRequest
    ) throws URISyntaxException {
        AssetPlantServiceReadingResponse assetPlantServiceReading = assetPlantServiceReadingService.save(assetPlantServiceReadingRequest);
        return ResponseEntity.created(new URI("/api/asset-plant-service-readings/" + assetPlantServiceReading.assetPlantServiceReadingId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    String.valueOf(assetPlantServiceReading.assetPlantServiceReadingId())
                )
            )
            .body(assetPlantServiceReading);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetPlantServiceReadingResponse> updateAssetPlantServiceReading(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AssetPlantServiceReadingRequest assetPlantServiceReadingRequest
    ) {
        AssetPlantServiceReadingResponse assetPlantServiceReading = assetPlantServiceReadingService.update(
            id,
            assetPlantServiceReadingRequest
        );
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    String.valueOf(assetPlantServiceReading.assetPlantServiceReadingId())
                )
            )
            .body(assetPlantServiceReading);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AssetPlantServiceReadingResponse> partialUpdateAssetPlantServiceReading(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AssetPlantServiceReadingRequest assetPlantServiceReadingRequest
    ) {
        AssetPlantServiceReadingResponse assetPlantServiceReading = assetPlantServiceReadingService.partialUpdate(
            id,
            assetPlantServiceReadingRequest
        );
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    String.valueOf(assetPlantServiceReading.assetPlantServiceReadingId())
                )
            )
            .body(assetPlantServiceReading);
    }

    @GetMapping("")
    public ResponseEntity<List<AssetPlantServiceReadingResponse>> getAllAssetPlantServiceReadings(
        @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
        @RequestParam(value = "size", defaultValue = "100", required = false) Integer size,
        @RequestParam(value = "assetPlantId", required = false) Long assetPlantId,
        @RequestParam(value = "isActive", required = false) Boolean isActive,
        @RequestParam(value = "serviceUnit", required = false) String serviceUnit
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("assetPlantServiceReadingId").descending());
        Page<AssetPlantServiceReadingResponse> assetPlantServiceReadings = assetPlantServiceReadingService.findAll(
            pageable,
            assetPlantId,
            isActive,
            serviceUnit
        );

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(),
            assetPlantServiceReadings
        );

        return ResponseEntity.ok().headers(headers).body(assetPlantServiceReadings.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetPlantServiceReadingResponse> getAssetPlantServiceReading(
        @PathVariable("id") Long assetPlantServiceReadingId
    ) {
        return ResponseEntity.ok().body(assetPlantServiceReadingService.findOne(assetPlantServiceReadingId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssetPlantServiceReading(@PathVariable("id") Long assetPlantServiceReadingId) {
        assetPlantServiceReadingService.delete(assetPlantServiceReadingId);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, String.valueOf(assetPlantServiceReadingId)))
            .build();
    }
}
