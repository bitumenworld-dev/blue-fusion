package com.bitumen.bluefusion.service.contractDivisionService;

import com.bitumen.bluefusion.service.contractDivisionService.dto.ContractDivisionRequest;
import com.bitumen.bluefusion.service.contractDivisionService.dto.ContractDivisionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContractDivisionService {
    ContractDivisionResponse save(ContractDivisionRequest contractDivisionRequest);

    ContractDivisionResponse update(Long contractDivisionId, ContractDivisionRequest contractDivisionRequest);

    ContractDivisionResponse partialUpdate(Long contractDivisionId, ContractDivisionRequest contractDivision);

    Page<ContractDivisionResponse> findAll(
        Pageable pageable,
        Long contractDivisionId,
        String contractDivisionNumber,
        String contractDivisionName
    );

    ContractDivisionResponse findOne(Long companyId, ContractDivisionRequest contractDivision);

    void delete(Long contractDivisionId, ContractDivisionRequest contractDivision);
}
