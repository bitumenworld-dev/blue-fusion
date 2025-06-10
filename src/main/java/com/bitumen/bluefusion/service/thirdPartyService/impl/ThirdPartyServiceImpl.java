package com.bitumen.bluefusion.service.thirdPartyService.impl;

import com.bitumen.bluefusion.domain.ThirdParty;
import com.bitumen.bluefusion.repository.ThirdPartyRepository;
import com.bitumen.bluefusion.service.exceptions.RecordNotFoundException;
import com.bitumen.bluefusion.service.thirdPartyService.ThirdPartyService;
import com.bitumen.bluefusion.service.thirdPartyService.dto.ThirdPartyMapper;
import com.bitumen.bluefusion.service.thirdPartyService.dto.ThirdPartyRequest;
import com.bitumen.bluefusion.service.thirdPartyService.dto.ThirdPartyResponse;
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
public class ThirdPartyServiceImpl implements ThirdPartyService {

    private final ThirdPartyRepository thirdPartyRepository;

    @Override
    public ThirdPartyResponse save(ThirdPartyRequest thirdPartyRequest) {
        ThirdParty thirdParty = ThirdParty.builder()
            .thirdPartyName(thirdPartyRequest.thirdPartyName())
            .thirdPartyShortName(thirdPartyRequest.thirdPartyShortName())
            .isActive(thirdPartyRequest.isActive())
            .usesFuelSystem(thirdPartyRequest.usesFuelSystem())
            .build();
        thirdParty = thirdPartyRepository.save(thirdParty);
        return ThirdPartyMapper.map.apply(thirdParty);
    }

    @Override
    public ThirdPartyResponse update(Long thirdPartyId, ThirdPartyRequest thirdPartyRequest) {
        ThirdParty thirdParty = thirdPartyRepository
            .findById(thirdPartyId)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Third Party not found: %s", thirdPartyId)));

        thirdParty.setThirdPartyName(thirdPartyRequest.thirdPartyName());
        thirdParty.setThirdPartyShortName(thirdPartyRequest.thirdPartyShortName());
        thirdParty.setIsActive(thirdPartyRequest.isActive());
        thirdParty.setUsesFuelSystem(thirdPartyRequest.usesFuelSystem());

        thirdParty = thirdPartyRepository.save(thirdParty);
        return ThirdPartyMapper.map.apply(thirdParty);
    }

    @Override
    public ThirdPartyResponse partialUpdate(Long thirdPartyId, ThirdPartyRequest thirdPartyRequest) {
        return thirdPartyRepository
            .findById(thirdPartyId)
            .map(existingThirdParty -> {
                Optional.ofNullable(thirdPartyRequest.thirdPartyName()).ifPresent(existingThirdParty::setThirdPartyName);
                Optional.ofNullable(thirdPartyRequest.thirdPartyShortName()).ifPresent(existingThirdParty::setThirdPartyShortName);
                Optional.ofNullable(thirdPartyRequest.isActive()).ifPresent(existingThirdParty::setIsActive);
                Optional.ofNullable(thirdPartyRequest.usesFuelSystem()).ifPresent(existingThirdParty::setUsesFuelSystem);
                return existingThirdParty;
            })
            .map(thirdPartyRepository::save)
            .map(ThirdPartyMapper.map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Third Party not found: %s", thirdPartyId)));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ThirdPartyResponse> findAll(Pageable pageable, String thirdPartyName, String thirdPartyShortName, Boolean isActive) {
        Specification<ThirdParty> specification = ThirdPartySpec.withThirdPartyName(thirdPartyName)
            .and(ThirdPartySpec.withThirdPartyShortName(thirdPartyShortName))
            .and(ThirdPartySpec.withIsActive(isActive));
        return thirdPartyRepository.findAll(specification, pageable).map(ThirdPartyMapper.map);
    }

    @Transactional(readOnly = true)
    @Override
    public ThirdPartyResponse findOne(Long thirdPartyId, ThirdPartyRequest thirdPartyRequest) {
        return thirdPartyRepository
            .findById(thirdPartyId)
            .map(ThirdPartyMapper.map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Third Party not found: %s", thirdPartyId)));
    }

    @Override
    public void delete(Long thirdPartyId) {
        if (!thirdPartyRepository.existsById(thirdPartyId)) {
            throw new RecordNotFoundException(String.format("Third Party not found: %s", thirdPartyId));
        }
        thirdPartyRepository.deleteById(thirdPartyId);
    }
}
