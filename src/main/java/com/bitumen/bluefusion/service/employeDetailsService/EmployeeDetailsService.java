package com.bitumen.bluefusion.service.employeDetailsService;

import com.bitumen.bluefusion.service.employeDetailsService.dto.EmployeeDetailsCriteria;
import com.bitumen.bluefusion.service.employeDetailsService.payload.EmployeeDetailsRequest;
import com.bitumen.bluefusion.service.employeDetailsService.payload.EmployeeDetailsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface EmployeeDetailsService {
    EmployeeDetailsResponse save(EmployeeDetailsRequest employeeDetailsRequest);

    EmployeeDetailsResponse update(long employeeId, EmployeeDetailsRequest employeeDetailsRequest);

    @Transactional(readOnly = true)
    Page<EmployeeDetailsResponse> findAll(Pageable pageable, EmployeeDetailsCriteria criteria);

    @Transactional(readOnly = true)
    EmployeeDetailsResponse findOne(Long employeeId);

    void delete(Long employeeId);
}
