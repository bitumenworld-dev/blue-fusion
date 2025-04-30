package com.bitumen.bluefusion.service.impl;

import com.bitumen.bluefusion.domain.ContractDivision;
import com.bitumen.bluefusion.repository.ContractDivisionRepository;
import com.bitumen.bluefusion.service.ContractDivisionService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.ContractDivision}.
 */
@Service
@Transactional
public class ContractDivisionServiceImpl implements ContractDivisionService {

    private static final Logger LOG = LoggerFactory.getLogger(ContractDivisionServiceImpl.class);

    private final ContractDivisionRepository contractDivisionRepository;

    public ContractDivisionServiceImpl(ContractDivisionRepository contractDivisionRepository) {
        this.contractDivisionRepository = contractDivisionRepository;
    }

    @Override
    public ContractDivision save(ContractDivision contractDivision) {
        LOG.debug("Request to save ContractDivision : {}", contractDivision);
        return contractDivisionRepository.save(contractDivision);
    }

    @Override
    public ContractDivision update(ContractDivision contractDivision) {
        LOG.debug("Request to update ContractDivision : {}", contractDivision);
        return contractDivisionRepository.save(contractDivision);
    }

    @Override
    public Optional<ContractDivision> partialUpdate(ContractDivision contractDivision) {
        LOG.debug("Request to partially update ContractDivision : {}", contractDivision);

        return contractDivisionRepository
            .findById(contractDivision.getId())
            .map(existingContractDivision -> {
                if (contractDivision.getContractDivisionId() != null) {
                    existingContractDivision.setContractDivisionId(contractDivision.getContractDivisionId());
                }
                if (contractDivision.getContractDivisionNumber() != null) {
                    existingContractDivision.setContractDivisionNumber(contractDivision.getContractDivisionNumber());
                }
                if (contractDivision.getCompanyId() != null) {
                    existingContractDivision.setCompanyId(contractDivision.getCompanyId());
                }
                if (contractDivision.getBuildSmartReference() != null) {
                    existingContractDivision.setBuildSmartReference(contractDivision.getBuildSmartReference());
                }
                if (contractDivision.getShiftStart() != null) {
                    existingContractDivision.setShiftStart(contractDivision.getShiftStart());
                }
                if (contractDivision.getShiftEnd() != null) {
                    existingContractDivision.setShiftEnd(contractDivision.getShiftEnd());
                }
                if (contractDivision.getType() != null) {
                    existingContractDivision.setType(contractDivision.getType());
                }
                if (contractDivision.getCompleted() != null) {
                    existingContractDivision.setCompleted(contractDivision.getCompleted());
                }

                return existingContractDivision;
            })
            .map(contractDivisionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContractDivision> findAll(Pageable pageable) {
        LOG.debug("Request to get all ContractDivisions");
        return contractDivisionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContractDivision> findOne(Long id) {
        LOG.debug("Request to get ContractDivision : {}", id);
        return contractDivisionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ContractDivision : {}", id);
        contractDivisionRepository.deleteById(id);
    }
}
