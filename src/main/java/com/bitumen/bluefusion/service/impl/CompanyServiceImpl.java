package com.bitumen.bluefusion.service.impl;

import com.bitumen.bluefusion.domain.Company;
import com.bitumen.bluefusion.repository.CompanyRepository;
import com.bitumen.bluefusion.service.CompanyService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.Company}.
 */
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private static final Logger LOG = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company save(Company company) {
        LOG.debug("Request to save Company : {}", company);
        return companyRepository.save(company);
    }

    @Override
    public Company update(Company company) {
        LOG.debug("Request to update Company : {}", company);
        return companyRepository.save(company);
    }

    @Override
    public Optional<Company> partialUpdate(Company company) {
        LOG.debug("Request to partially update Company : {}", company);

        return companyRepository
            .findById(company.getId())
            .map(existingCompany -> {
                if (company.getCompanyId() != null) {
                    existingCompany.setCompanyId(company.getCompanyId());
                }
                if (company.getName() != null) {
                    existingCompany.setName(company.getName());
                }
                if (company.getDescription() != null) {
                    existingCompany.setDescription(company.getDescription());
                }

                return existingCompany;
            })
            .map(companyRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Company> findAll(Pageable pageable) {
        LOG.debug("Request to get all Companies");
        return companyRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Company> findOne(Long id) {
        LOG.debug("Request to get Company : {}", id);
        return companyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Company : {}", id);
        companyRepository.deleteById(id);
    }
}
