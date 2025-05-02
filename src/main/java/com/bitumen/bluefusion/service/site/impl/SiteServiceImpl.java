package com.bitumen.bluefusion.service.site.impl;

import com.bitumen.bluefusion.domain.Company;
import com.bitumen.bluefusion.domain.Site;
import com.bitumen.bluefusion.repository.CompanyRepository;
import com.bitumen.bluefusion.repository.SiteRepository;
import com.bitumen.bluefusion.service.exceptions.RecordNotFoundException;
import com.bitumen.bluefusion.service.site.SiteService;
import com.bitumen.bluefusion.service.site.dto.SiteRequest;
import com.bitumen.bluefusion.service.site.dto.SiteResponse;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SiteServiceImpl implements SiteService {

    private static final Logger LOG = LoggerFactory.getLogger(SiteServiceImpl.class);

    private final SiteRepository siteRepository;
    private final CompanyRepository companyRepository;

    @Override
    public SiteResponse save(SiteRequest siteRequest) {
        Company company = companyRepository
            .findById(siteRequest.companyId())
            .orElseThrow(() -> new RecordNotFoundException(String.format("Company with id %s not found", siteRequest.companyId())));

        Site site = Site.builder()
            .siteName(siteRequest.siteName())
            .latitude(siteRequest.latitude())
            .longitude(siteRequest.longitude())
            .siteNotes(siteRequest.siteNotes())
            .siteImage(siteRequest.image())
            .company(company)
            .build();
        Site savedSite = siteRepository.save(site);
        return SiteResponseMapper.map.apply(savedSite);
    }

    @Override
    public SiteResponse update(Long siteId, SiteRequest siteRequest) {
        Site site = siteRepository
            .findById(siteId)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Site with id %s not found", siteId)));
        Company company = companyRepository
            .findById(siteRequest.companyId())
            .orElseThrow(() -> new RecordNotFoundException(String.format("Company with id %s not found", siteRequest.companyId())));
        site.setSiteName(siteRequest.siteName());
        site.setLatitude(siteRequest.latitude());
        site.setLongitude(siteRequest.longitude());
        site.setSiteNotes(siteRequest.siteNotes());
        site.setSiteImage(siteRequest.image());
        site.setCompany(company);
        LOG.debug("Request to update Site : {}", site);
        Site savedSite = siteRepository.save(site);
        return SiteResponseMapper.map.apply(savedSite);
    }

    @Override
    public SiteResponse partialUpdate(Long siteId, SiteRequest siteRequest) {
        LOG.debug("Request to partially update Site : {}", siteRequest);

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
                Optional.ofNullable(siteRequest.image()).ifPresent(existingSite::setSiteImage);
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
        LOG.debug("Request to get all Sites");
        return siteRepository.findAll(pageable).map(SiteResponseMapper.map);
    }

    @Override
    @Transactional(readOnly = true)
    public SiteResponse findOne(Long id) {
        LOG.debug("Request to get Site : {}", id);
        Site site = siteRepository
            .findBySiteId(id)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Site with id %s not found", id)));
        return SiteResponseMapper.map.apply(site);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Site : {}", id);
        siteRepository
            .findBySiteId(id)
            .ifPresentOrElse(siteRepository::delete, () -> {
                throw new RecordNotFoundException(String.format("Site with id %s not found", id));
            });
    }
}

interface SiteResponseMapper {
    Function<Site, SiteResponse> map = site ->
        new SiteResponse(
            site.getSiteId(),
            site.getSiteName(),
            site.getLatitude(),
            site.getLongitude(),
            site.getSiteNotes(),
            site.getCompany().getName(),
            site.getSiteImage()
        );
}
