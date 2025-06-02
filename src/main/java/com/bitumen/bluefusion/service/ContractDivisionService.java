package com.bitumen.bluefusion.service;

import com.bitumen.bluefusion.domain.ContractDivision;
import com.bitumen.bluefusion.service.contractDivisionService.dto.ContractDivisionRequest;
import com.bitumen.bluefusion.service.contractDivisionService.dto.ContractDivisionResponse;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Interface for managing {@link com.bitumen.bluefusion.domain.ContractDivision}.
 */
public interface ContractDivisionService {
    ContractDivisionResponse save(ContractDivisionRequest contractDivisionRequest);

    ContractDivisionResponse update(Long contractDivisionId, ContractDivisionRequest contractDivisionRequest);

    ContractDivisionResponse partialupdate(Long contractDivisionId, ContractDivisionRequest contractDivision);

    @Transactional(readOnly = true)
    Page<ContractDivisionResponse> findAll(
        Pageable pageable,
        Long contractDivisionId,
        String contractDivisionNumber,
        String contractDivisionName
    );

    @Transactional(readOnly = true)
    ContractDivisionResponse findOne(Long contractDivisionId, ContractDivisionRequest contractDivision);

    void delete(Long contractDivisionId, ContractDivisionRequest contractDivision);
}
