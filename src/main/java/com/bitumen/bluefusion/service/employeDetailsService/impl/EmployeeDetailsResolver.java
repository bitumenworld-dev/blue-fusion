package com.bitumen.bluefusion.service.employeDetailsService.impl;

import com.bitumen.bluefusion.domain.EmployeeDetails;
import com.bitumen.bluefusion.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeDetailsResolver {

    private final CompanyRepository companyRepository;

    public EmployeeDetails resolveEntitiesForCriteria(EmployeeDetails employeeDetails) {
        if (employeeDetails.getCompany() != null && employeeDetails.getCompany().getCompanyId() != null) {
            employeeDetails.setCompany(
                companyRepository
                    .findById(employeeDetails.getCompany().getCompanyId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid company ID"))
            );
        }
        return employeeDetails;
    }
}
