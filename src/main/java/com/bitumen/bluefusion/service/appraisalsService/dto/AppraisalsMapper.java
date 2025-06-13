package com.bitumen.bluefusion.service.appraisalsService.dto;

import com.bitumen.bluefusion.domain.Appraisals;
import java.util.function.Function;

public interface AppraisalsMapper {
    Function<Appraisals, AppraisalsResponse> map = appraisals ->
        new AppraisalsResponse(
            appraisals.getAppraisalId(),
            appraisals.getEmployeeNumber(),
            appraisals.getAppraisalPeriod(),
            appraisals.getAppraisalValue()
        );
}
