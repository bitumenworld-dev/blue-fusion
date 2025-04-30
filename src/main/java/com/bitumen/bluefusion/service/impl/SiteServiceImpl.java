package com.bitumen.bluefusion.service.impl;

import com.bitumen.bluefusion.domain.Site;
import com.bitumen.bluefusion.repository.SiteRepository;
import com.bitumen.bluefusion.service.SiteService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.Site}.
 */
@Service
@Transactional
public class SiteServiceImpl implements SiteService {

    private static final Logger LOG = LoggerFactory.getLogger(SiteServiceImpl.class);

    private final SiteRepository siteRepository;

    public SiteServiceImpl(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    @Override
    public Site save(Site site) {
        LOG.debug("Request to save Site : {}", site);
        return siteRepository.save(site);
    }

    @Override
    public Site update(Site site) {
        LOG.debug("Request to update Site : {}", site);
        return siteRepository.save(site);
    }

    @Override
    public Optional<Site> partialUpdate(Site site) {
        LOG.debug("Request to partially update Site : {}", site);

        return siteRepository
            .findById(site.getId())
            .map(existingSite -> {
                if (site.getSiteId() != null) {
                    existingSite.setSiteId(site.getSiteId());
                }
                if (site.getSiteName() != null) {
                    existingSite.setSiteName(site.getSiteName());
                }
                if (site.getLatitude() != null) {
                    existingSite.setLatitude(site.getLatitude());
                }
                if (site.getLongitude() != null) {
                    existingSite.setLongitude(site.getLongitude());
                }
                if (site.getIsActive() != null) {
                    existingSite.setIsActive(site.getIsActive());
                }
                if (site.getSiteNotes() != null) {
                    existingSite.setSiteNotes(site.getSiteNotes());
                }
                if (site.getSiteImageUrl() != null) {
                    existingSite.setSiteImageUrl(site.getSiteImageUrl());
                }
                if (site.getCompanyId() != null) {
                    existingSite.setCompanyId(site.getCompanyId());
                }

                return existingSite;
            })
            .map(siteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Site> findAll(Pageable pageable) {
        LOG.debug("Request to get all Sites");
        return siteRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Site> findOne(Long id) {
        LOG.debug("Request to get Site : {}", id);
        return siteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Site : {}", id);
        siteRepository.deleteById(id);
    }
}
