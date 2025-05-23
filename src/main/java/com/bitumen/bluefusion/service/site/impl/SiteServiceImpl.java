package com.bitumen.bluefusion.service.site.impl;

import com.bitumen.bluefusion.domain.Company;
import com.bitumen.bluefusion.domain.Site;
import com.bitumen.bluefusion.repository.CompanyRepository;
import com.bitumen.bluefusion.repository.SiteRepository;
import com.bitumen.bluefusion.service.exceptions.RecordNotFoundException;
import com.bitumen.bluefusion.service.site.SiteService;
import com.bitumen.bluefusion.service.site.dto.SiteRequest;
import com.bitumen.bluefusion.service.site.dto.SiteResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SiteServiceImpl implements SiteService {

    private final SiteRepository siteRepository;
    private final CompanyRepository companyRepository;

    @Override
    public SiteResponse save(SiteRequest siteRequest) throws IOException {
        Site site = Site.builder()
            .siteName(siteRequest.siteName())
            .latitude(siteRequest.latitude())
            .longitude(siteRequest.longitude())
            .siteNotes(siteRequest.siteNotes())
            .build();

        if (siteRequest.companyId() != null) {
            Company company = companyRepository
                .findById(siteRequest.companyId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Company with id %s not found", siteRequest.companyId())));
            site.setCompany(company);
        }

        Site savedSite = siteRepository.save(site);
        return SiteResponseMapper.map.apply(savedSite);
    }

    @Override
    public SiteResponse uploadSiteImage(Long siteId, MultipartFile file) throws IOException {
        Site site = siteRepository
            .findById(siteId)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Site with id %s not found", siteId)));

        site.setSiteImage(file.getBytes());
        return SiteResponseMapper.map.apply(siteRepository.save(site));
    }

    @Override
    public SiteResponse update(Long siteId, SiteRequest siteRequest) throws IOException {
        Site site = siteRepository
            .findById(siteId)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Site with id %s not found", siteId)));

        if (siteRequest.companyId() != null) {
            Company company = companyRepository
                .findById(siteRequest.companyId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Company with id %s not found", siteRequest.companyId())));
            site.setCompany(company);
        }

        site.setSiteName(siteRequest.siteName());
        site.setLatitude(siteRequest.latitude());
        site.setLongitude(siteRequest.longitude());
        site.setSiteNotes(siteRequest.siteNotes());

        Site savedSite = siteRepository.save(site);
        return SiteResponseMapper.map.apply(savedSite);
    }

    @Override
    public SiteResponse partialUpdate(Long siteId, SiteRequest siteRequest) {
        Company company;
        if (!Objects.isNull(siteRequest.companyId()) && siteRequest.companyId() > 0L) {
            company = companyRepository
                .findById(siteRequest.companyId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Company with id %s not found", siteRequest.companyId())));
        } else {
            company = null;
        }

        return siteRepository
            .findById(siteId)
            .map(existingSite -> {
                // Update non-relationship fields using Optional to handle nulls cleanly
                Optional.ofNullable(siteRequest.siteName()).ifPresent(existingSite::setSiteName);
                Optional.ofNullable(siteRequest.latitude()).ifPresent(existingSite::setLatitude);
                Optional.ofNullable(siteRequest.longitude()).ifPresent(existingSite::setLongitude);
                Optional.ofNullable(siteRequest.siteNotes()).ifPresent(existingSite::setSiteNotes);
                Optional.ofNullable(company).ifPresent(existingSite::setCompany);
                return existingSite;
            })
            .map(siteRepository::save)
            .map(SiteResponseMapper.map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Company with id %s not found", siteRequest.companyId())));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SiteResponse> findAll(Pageable pageable) {
        return siteRepository.findAll(pageable).map(SiteResponseMapper.map);
    }

    @Override
    @Transactional(readOnly = true)
    public SiteResponse findOne(Long id) {
        Site site = siteRepository
            .findBySiteId(id)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Site with id %s not found", id)));
        return SiteResponseMapper.map.apply(site);
    }

    @Override
    public void delete(Long id) {
        siteRepository
            .findBySiteId(id)
            .ifPresentOrElse(siteRepository::delete, () -> {
                throw new RecordNotFoundException(String.format("Site with id %s not found", id));
            });
    }
}
