package com.bitumen.bluefusion.service.makeService.impl;

import com.bitumen.bluefusion.domain.Make;
import com.bitumen.bluefusion.repository.MakeRepository;
import com.bitumen.bluefusion.service.exceptions.RecordNotFoundException;
import com.bitumen.bluefusion.service.makeService.MakeService;
import com.bitumen.bluefusion.service.makeService.dto.MakeRequest;
import com.bitumen.bluefusion.service.makeService.dto.MakeResponse;
import com.bitumen.bluefusion.service.makeService.dto.MakeResponseMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Make}.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MakeServiceImpl implements MakeService {

    private final MakeRepository makeRepository;

    @Override
    public MakeResponse save(MakeRequest makeRequest) {
        Make make = Make.builder().make(makeRequest.make()).build();
        make = makeRepository.save(make);
        return MakeResponseMapper.map.apply(make);
    }

    @Override
    public MakeResponse update(Long id, MakeRequest makeRequest) {
        Make make = makeRepository
            .findById(id)
            .orElseThrow(() -> new RecordNotFoundException(String.format("make with id %s not found", id)));
        make.setMake(makeRequest.make());
        make = makeRepository.save(make);
        return MakeResponseMapper.map.apply(make);
    }

    @Override
    public MakeResponse partialUpdate(Long id, MakeRequest makeRequest) {
        return makeRepository
            .findById(id)
            .map(existingMake -> {
                Optional.ofNullable(makeRequest.make()).ifPresent(existingMake::setMake);
                return existingMake;
            })
            .map(makeRepository::save)
            .map(MakeResponseMapper.map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("make with id %s not found", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MakeResponse> findAll(Pageable pageable, String make) {
        Specification<Make> specification = MakeSpec.withMake(make);
        return makeRepository.findAll(specification, pageable).map(MakeResponseMapper.map);
    }

    @Override
    @Transactional(readOnly = true)
    public MakeResponse findOne(Long id) {
        return makeRepository
            .findById(id)
            .map(MakeResponseMapper.map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("make with id %s not found", id)));
    }

    @Override
    public void delete(Long id) {
        makeRepository
            .findById(id)
            .ifPresentOrElse(makeRepository::delete, () -> {
                throw new RecordNotFoundException(String.format("make with id %s not found", id));
            });
    }
}
