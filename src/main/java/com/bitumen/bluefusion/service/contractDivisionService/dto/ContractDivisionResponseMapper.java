package com.bitumen.bluefusion.service.contractDivisionService.dto;

import com.bitumen.bluefusion.domain.ContractDivision;
import java.util.function.Function;

public interface ContractDivisionResponseMapper {
    Function<ContractDivision, ContractDivisionResponse> map = contractDivision ->
        new ContractDivisionResponse(
            contractDivision.getContractDivisionId(),
            contractDivision.getContractDivisionNumber(),
            contractDivision.getContractDivisionName(),
            contractDivision.getCompany() != null ? contractDivision.getCompany().getCompanyId() : null,
            contractDivision.getBuildSmartReference(),
            contractDivision.getShiftStart(),
            contractDivision.getContractDivisionType(),
            contractDivision.getCompleted(),
            contractDivision.getHrHoursMondayThursday(),
            contractDivision.getHrHoursFriday(),
            contractDivision.getAddHoursMondayFriday(),
            contractDivision.getAddHoursWeekend()
        );
}
