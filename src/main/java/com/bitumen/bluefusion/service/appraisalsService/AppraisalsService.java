package com.bitumen.bluefusion.service.appraisalsService;

import com.bitumen.bluefusion.service.appraisalsService.dto.AppraisalsRequest;
import com.bitumen.bluefusion.service.appraisalsService.dto.AppraisalsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface AppraisalsService {
    AppraisalsResponse save(AppraisalsRequest appraisalsRequest);

    AppraisalsResponse update(Long appraisalId, AppraisalsRequest appraisalsRequest);

    @Transactional(readOnly = true)
    AppraisalsResponse partialUpdate(Long appraisalId, AppraisalsRequest appraisalsRequest);

    @Transactional(readOnly = true)
    AppraisalsResponse findOne(Long appraisalId);

    @Transactional(readOnly = true)
    Page<AppraisalsResponse> findAll(Pageable pageable, String employeeNumber, Integer appraisalPeriod, Integer appraisalValue);

    void delete(Long appraisalId);
}
