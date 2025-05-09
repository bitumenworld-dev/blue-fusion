package com.bitumen.bluefusion.service.companyService.impl;

import com.bitumen.bluefusion.domain.Company;
import com.bitumen.bluefusion.service.companyService.dto.CompanyResponse;
import java.util.function.Function;

interface CompanyResponseMapper {
    Function<Company, CompanyResponse> map = company ->
        new CompanyResponse(
            company.getCompanyId(),
            company.getName(),
            company.getAddress(),
            company.getDescription(),
            company.getUsesFuelSystem(),
            company.getIsActive()
        );
}
