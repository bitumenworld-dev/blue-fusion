package com.bitumen.bluefusion.service.companyService.impl;

import com.bitumen.bluefusion.domain.Company;
import com.bitumen.bluefusion.repository.CompanyRepository;
import com.bitumen.bluefusion.service.companyService.CompanyService;
import com.bitumen.bluefusion.service.companyService.dto.CompanyRequest;
import com.bitumen.bluefusion.service.companyService.dto.CompanyResponse;
import com.bitumen.bluefusion.service.exceptions.RecordNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.Company}.
 */
@Slf4j
@Service
//@Transactional
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public CompanyResponse save(CompanyRequest companyRequest) {
        Company company = Company.builder()
            .name(companyRequest.name())
            .access_key(companyRequest.access_key())
            .address(companyRequest.address())
            .isActive(companyRequest.isActive())
            .isIAC(companyRequest.isIAC())
            .usesFuelSystem(companyRequest.usesFuelSystem())
            .build();

        Company savedCompany = companyRepository.save(company);

        return CompanyResponseMapper.map.apply(savedCompany);
    }

    @Override
    public CompanyResponse update(Long companyId, CompanyRequest companyRequest) {
        Company company = companyRepository
            .findById(companyId)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Company with id %s not found", companyId)));
        company.setName(companyRequest.name());
        company.setAccess_key(companyRequest.access_key());
        company.setAddress(companyRequest.address());
        company.setIsActive(companyRequest.isActive());
        company.setIsIAC(companyRequest.isIAC());
        company.setUsesFuelSystem(companyRequest.usesFuelSystem());

        return CompanyResponseMapper.map.apply(companyRepository.save(company));
    }

    @Override
    public CompanyResponse partialUpdate(Long companyId, CompanyRequest companyRequest) {
        log.debug("Request to partially update Company : {}", companyRequest);

        return companyRepository
            .findById(companyId)
            .map(existingCompany -> {
                Optional.ofNullable(existingCompany.getName()).ifPresent(existingCompany::setName);
                Optional.ofNullable(existingCompany.getAccess_key()).ifPresent(existingCompany::setAccess_key);
                Optional.ofNullable(existingCompany.getAddress()).ifPresent(existingCompany::setAddress);
                Optional.ofNullable(existingCompany.getIsActive()).ifPresent(existingCompany::setIsActive);
                Optional.ofNullable(existingCompany.getIsIAC()).ifPresent(existingCompany::setIsIAC);
                Optional.ofNullable(existingCompany.getUsesFuelSystem()).ifPresent(existingCompany::setUsesFuelSystem);
                return existingCompany;
            })
            .map(companyRepository::save)
            .map(CompanyResponseMapper.map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Company with id %s not found", companyId)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyResponse> findAll(Pageable pageable, String companyName, Boolean usesFuelSystem, Boolean isActive) {
        Specification<Company> specification = CompanySpec.withCompanyNameLike(companyName)
            .and(CompanySpec.isActive(isActive))
            .and(CompanySpec.isIAC(isActive))
            .and(CompanySpec.usesFuelSystem(usesFuelSystem));

        return companyRepository.findAll(specification, pageable).map(CompanyResponseMapper.map);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyResponse findByCompanyId(Long id) {
        Company company = companyRepository
            .findById(id)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Company with id %s not found", id)));
        return CompanyResponseMapper.map.apply(company);
    }

    @Override
    public void delete(Long id) {
        companyRepository
            .findById(id)
            .ifPresentOrElse(companyRepository::delete, () -> {
                throw new RecordNotFoundException(String.format("Company with id %s not found", id));
            });
    }
}
