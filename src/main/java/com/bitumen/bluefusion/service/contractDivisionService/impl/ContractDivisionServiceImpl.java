package com.bitumen.bluefusion.service.contractDivisionService.impl;

import com.bitumen.bluefusion.domain.Company;
import com.bitumen.bluefusion.domain.ContractDivision;
import com.bitumen.bluefusion.repository.CompanyRepository;
import com.bitumen.bluefusion.repository.ContractDivisionRepository;
import com.bitumen.bluefusion.service.contractDivisionService.ContractDivisionService;
import com.bitumen.bluefusion.service.contractDivisionService.dto.ContractDivisionRequest;
import com.bitumen.bluefusion.service.contractDivisionService.dto.ContractDivisionResponse;
import com.bitumen.bluefusion.service.contractDivisionService.dto.ContractDivisionResponseMapper;
import com.bitumen.bluefusion.service.exceptions.RecordNotFoundException;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.ContractDivision}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ContractDivisionServiceImpl implements ContractDivisionService {

    private static final Logger LOG = LoggerFactory.getLogger(ContractDivisionServiceImpl.class);

    private final ContractDivisionRepository contractDivisionRepository;

    private final CompanyRepository companyRepository;

    @Override
    public ContractDivisionResponse save(ContractDivisionRequest contractDivisionRequest) {
        Company company = companyRepository
            .findById(contractDivisionRequest.companyId())
            .orElseThrow(() -> new RecordNotFoundException(String.format("Company not found: %s", contractDivisionRequest.companyId())));
        ContractDivision contractDivision = ContractDivision.builder()
            .contractDivisionNumber(contractDivisionRequest.contractDivisionNumber())
            .company(company)
            .buildSmartReference(contractDivisionRequest.buildSmartReference())
            .shiftStart(contractDivisionRequest.shiftStart())
            .contractDivisionType(contractDivisionRequest.contractDivisionType())
            .completed(contractDivisionRequest.completed())
            .build();
        contractDivision = contractDivisionRepository.save(contractDivision);
        return ContractDivisionResponseMapper.map.apply(contractDivision);
    }

    @Override
    public ContractDivisionResponse update(Long contractDivisionId, ContractDivisionRequest contractDivisionRequest) {
        ContractDivision contractDivision = contractDivisionRepository
            .findById(contractDivisionId)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Contract Division not found: %s", contractDivisionId)));
        Company company = companyRepository
            .findById(contractDivisionRequest.companyId())
            .orElseThrow(() -> new RecordNotFoundException(String.format("Company not found: %s", contractDivisionRequest.companyId())));
        contractDivision.setContractDivisionNumber(contractDivisionRequest.contractDivisionNumber());
        contractDivision.setCompany(company);
        contractDivision.setContractDivisionName(contractDivisionRequest.contractDivisionName());
        contractDivision.setBuildSmartReference(contractDivisionRequest.buildSmartReference());
        contractDivision.setShiftStart(contractDivisionRequest.shiftStart());
        contractDivision.setContractDivisionType(contractDivisionRequest.contractDivisionType());
        contractDivision.setCompleted(contractDivisionRequest.completed());
        contractDivision = contractDivisionRepository.save(contractDivision);
        return ContractDivisionResponseMapper.map.apply(contractDivision);
    }

    @Override
    public ContractDivisionResponse partialupdate(Long contractDivisionId, ContractDivisionRequest contractDivision) {
        return contractDivisionRepository
            .findById(contractDivisionId)
            .map(existingContractDivision -> {
                Optional.ofNullable(contractDivision.contractDivisionNumber()).ifPresent(
                    existingContractDivision::setContractDivisionNumber
                );
                Optional.ofNullable(contractDivision.companyId()).ifPresent(companyId -> {
                    Company company = companyRepository
                        .findById(companyId)
                        .orElseThrow(() -> new RecordNotFoundException(String.format("Company not found: %s", companyId)));
                    existingContractDivision.setCompany(company);
                });
                Optional.ofNullable(contractDivision.contractDivisionName()).ifPresent(existingContractDivision::setContractDivisionName);
                Optional.ofNullable(contractDivision.buildSmartReference()).ifPresent(existingContractDivision::setBuildSmartReference);
                Optional.ofNullable(contractDivision.shiftStart()).ifPresent(existingContractDivision::setShiftStart);
                Optional.ofNullable(contractDivision.contractDivisionType()).ifPresent(existingContractDivision::setContractDivisionType);
                Optional.ofNullable(contractDivision.completed()).ifPresent(existingContractDivision::setCompleted);
                return existingContractDivision;
            })
            .map(contractDivisionRepository::save)
            .map(ContractDivisionResponseMapper.map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Contract Division not found: %s", contractDivisionId)));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ContractDivisionResponse> findAll(
        Pageable pageable,
        Long companyId,
        String contractDivisionNumber,
        String contractDivisionName
    ) {
        ContractDivision contractDivision = null;
        if (!Objects.isNull(companyId)) {
            contractDivision = contractDivisionRepository
                .findById(companyId)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Contract Division not found: %s", companyId)));
        }

        Specification<ContractDivision> specification = ContractDivisionSpec.withcompanyId(companyId)
            .and(ContractDivisionSpec.withContractDivisionNumber(contractDivisionNumber))
            .and(ContractDivisionSpec.withContractDivisionName(contractDivisionName));

        Page<ContractDivisionResponse> contractDivisions = contractDivisionRepository
            .findAll(specification, pageable)
            .map(ContractDivisionResponseMapper.map);
        LOG.info("Find all ContractDivisions with specification {}", contractDivisions);
        return contractDivisions;
    }

    @Transactional(readOnly = true)
    @Override
    public ContractDivisionResponse findOne(Long companyId, ContractDivisionRequest contractDivision) {
        return contractDivisionRepository
            .findById(companyId)
            .map(ContractDivisionResponseMapper.map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Contract Division with id %s not found", companyId)));
    }

    @Override
    public void delete(Long contractDivisionId, ContractDivisionRequest contractDivision) {
        contractDivisionRepository
            .findById(contractDivisionId)
            .ifPresentOrElse(contractDivisionRepository::delete, () -> {
                throw new RecordNotFoundException(String.format("Contract Division with id %s not found", contractDivisionId));
            });
    }
}
