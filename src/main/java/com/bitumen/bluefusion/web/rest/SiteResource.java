package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.service.site.SiteService;
import com.bitumen.bluefusion.service.site.dto.SiteRequest;
import com.bitumen.bluefusion.service.site.dto.SiteResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bitumen.bluefusion.domain.Site}.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sites")
public class SiteResource {

    private static final String ENTITY_NAME = "site";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SiteService siteService;

    @PostMapping("")
    public ResponseEntity<SiteResponse> createSite(@RequestBody SiteRequest siteRequest) throws URISyntaxException, IOException {
        SiteResponse siteResponse = siteService.save(siteRequest);
        return ResponseEntity.created(new URI("/api/sites/" + siteResponse.id()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, siteResponse.id().toString()))
            .body(siteResponse);
    }

    @PutMapping("/upload-image/{id}")
    public ResponseEntity<SiteResponse> uploadSiteImage(
        @PathVariable(value = "id") final Long id,
        @RequestPart(value = "file") MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok().body(siteService.uploadSiteImage(id, file));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SiteResponse> updateSite(@PathVariable(value = "id") final Long id, @RequestBody SiteRequest siteRequest)
        throws IOException {
        SiteResponse siteResponse = siteService.update(id, siteRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siteResponse.id().toString()))
            .body(siteResponse);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SiteResponse> partialUpdateSite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SiteRequest siteRequest
    ) {
        SiteResponse result = siteService.partialUpdate(id, siteRequest);
        return ResponseUtil.wrapOrNotFound(
            Optional.of(result),
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.id().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<SiteResponse>> getAllSites(Pageable pageable) {
        Page<SiteResponse> page = siteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiteResponse> getSite(@PathVariable("id") Long id) {
        SiteResponse site = siteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.of(site));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable("id") Long id) {
        siteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
