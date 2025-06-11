package com.bitumen.bluefusion.service.appraisalsService.impl;

import com.bitumen.bluefusion.domain.Appraisals;
import com.bitumen.bluefusion.repository.AppraisalsRepository;
import com.bitumen.bluefusion.service.appraisalsService.AppraisalsService;
import com.bitumen.bluefusion.service.appraisalsService.dto.AppraisalsMapper;
import com.bitumen.bluefusion.service.appraisalsService.dto.AppraisalsRequest;
import com.bitumen.bluefusion.service.appraisalsService.dto.AppraisalsResponse;
import com.bitumen.bluefusion.service.exceptions.RecordNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AppraisalsServiceImpl implements AppraisalsService {

    private final AppraisalsRepository appraisalsRepository;

    @Override
    public AppraisalsResponse save(AppraisalsRequest appraisalsRequest) {
        Appraisals appraisals = Appraisals.builder()
            .employeeNumber(appraisalsRequest.employeeNumber())
            .appraisalPeriod(appraisalsRequest.appraisalPeriod())
            .appraisalValue(appraisalsRequest.appraisalValue())
            .build();
        appraisals = appraisalsRepository.save(appraisals);
        return AppraisalsMapper.map.apply(appraisals);
    }

    @Override
    public AppraisalsResponse update(Long appraisalId, AppraisalsRequest appraisalsRequest) {
        Appraisals appraisals = appraisalsRepository
            .findById(appraisalId)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Appraisal not found: %s", appraisalId)));

        appraisals.setEmployeeNumber(appraisalsRequest.employeeNumber());
        appraisals.setAppraisalPeriod(appraisalsRequest.appraisalPeriod());
        appraisals.setAppraisalValue(appraisalsRequest.appraisalValue());

        appraisals = appraisalsRepository.save(appraisals);
        return AppraisalsMapper.map.apply(appraisals);
    }

    @Override
    public AppraisalsResponse partialUpdate(Long appraisalId, AppraisalsRequest appraisalsRequest) {
        return appraisalsRepository
            .findById(appraisalId)
            .map(existingAppraisal -> {
                Optional.ofNullable(appraisalsRequest.employeeNumber()).ifPresent(existingAppraisal::setEmployeeNumber);
                Optional.ofNullable(appraisalsRequest.appraisalPeriod()).ifPresent(existingAppraisal::setAppraisalPeriod);
                Optional.ofNullable(appraisalsRequest.appraisalValue()).ifPresent(existingAppraisal::setAppraisalValue);
                return AppraisalsMapper.map.apply(appraisalsRepository.save(existingAppraisal));
            })
            .orElseThrow(() -> new RecordNotFoundException(String.format("Appraisal not found: %s", appraisalId)));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AppraisalsResponse> findAll(Pageable pageable, String employeeNumber, Integer appraisalPeriod, Integer appraisalValue) {
        Specification<Appraisals> specification = AppraisalsSpec.withEmployeeNumber(employeeNumber)
            .and(AppraisalsSpec.withAppraisalPeriod(appraisalPeriod))
            .and(AppraisalsSpec.withAppraisalValue(appraisalValue));
        return appraisalsRepository.findAll(specification, pageable).map(AppraisalsMapper.map);
    }

    @Transactional(readOnly = true)
    @Override
    public AppraisalsResponse findOne(Long appraisalId) {
        return appraisalsRepository
            .findById(appraisalId)
            .map(AppraisalsMapper.map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Appraisal not found: %s", appraisalId)));
    }

    @Override
    public void delete(Long appraisalId) {
        if (!appraisalsRepository.existsById(appraisalId)) {
            throw new RecordNotFoundException(String.format("Appraisal not found: %s", appraisalId));
        }
        appraisalsRepository.deleteById(appraisalId);
    }
}
