package com.bitumen.bluefusion.service.impl;

import com.bitumen.bluefusion.domain.SiteContract;
import com.bitumen.bluefusion.repository.SiteContractRepository;
import com.bitumen.bluefusion.service.SiteContractService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.SiteContract}.
 */
@Service
@Transactional
public class SiteContractServiceImpl implements SiteContractService {

    private static final Logger LOG = LoggerFactory.getLogger(SiteContractServiceImpl.class);

    private final SiteContractRepository siteContractRepository;

    public SiteContractServiceImpl(SiteContractRepository siteContractRepository) {
        this.siteContractRepository = siteContractRepository;
    }

    @Override
    public SiteContract save(SiteContract siteContract) {
        LOG.debug("Request to save SiteContract : {}", siteContract);
        return siteContractRepository.save(siteContract);
    }

    @Override
    public SiteContract update(SiteContract siteContract) {
        LOG.debug("Request to update SiteContract : {}", siteContract);
        return siteContractRepository.save(siteContract);
    }

    @Override
    public Optional<SiteContract> partialUpdate(SiteContract siteContract) {
        LOG.debug("Request to partially update SiteContract : {}", siteContract);

        return siteContractRepository
            .findById(siteContract.getId())
            .map(existingSiteContract -> {
                if (siteContract.getSiteContractId() != null) {
                    existingSiteContract.setSiteContractId(siteContract.getSiteContractId());
                }
                if (siteContract.getSiteId() != null) {
                    existingSiteContract.setSiteId(siteContract.getSiteId());
                }
                if (siteContract.getContractId() != null) {
                    existingSiteContract.setContractId(siteContract.getContractId());
                }

                return existingSiteContract;
            })
            .map(siteContractRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SiteContract> findAll(Pageable pageable) {
        LOG.debug("Request to get all SiteContracts");
        return siteContractRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SiteContract> findOne(Long id) {
        LOG.debug("Request to get SiteContract : {}", id);
        return siteContractRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete SiteContract : {}", id);
        siteContractRepository.deleteById(id);
    }
}
