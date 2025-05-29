package com.bitumen.bluefusion.service.makeModelService.impl;

import com.bitumen.bluefusion.domain.MakeModel;
import com.bitumen.bluefusion.repository.MakeModelRepository;
import com.bitumen.bluefusion.service.exceptions.RecordNotFoundException;
import com.bitumen.bluefusion.service.makeModelService.MakeModelService;
import com.bitumen.bluefusion.service.makeModelService.dto.MakeModelRequest;
import com.bitumen.bluefusion.service.makeModelService.dto.MakeModelResponse;
import com.bitumen.bluefusion.service.makeModelService.dto.MakeModelResponseMapper;
import java.util.Optional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Data
@Service
@Transactional
@RequiredArgsConstructor
public class MakeModelServiceImpl implements MakeModelService {

    private final MakeModelRepository makeModelRepository;

    @Override
    public MakeModelResponse save(MakeModelRequest makeModelRequest) {
        MakeModel makeModel = MakeModel.builder().model(makeModelRequest.model()).build();
        makeModel = makeModelRepository.save(makeModel);
        return MakeModelResponseMapper.map.apply(makeModel);
    }

    @Override
    public MakeModelResponse update(Long id, MakeModelRequest makeModelRequest) {
        MakeModel makeModel = makeModelRepository
            .findById(id)
            .orElseThrow(() -> new RecordNotFoundException(String.format("No record found for id %d", id)));
        makeModel.setModel(makeModelRequest.model());

        makeModel = makeModelRepository.save(makeModel);
        return MakeModelResponseMapper.map.apply(makeModel);
    }

    @Override
    public MakeModelResponse partialUpdate(Long id, MakeModelRequest makeModelRequest) {
        return makeModelRepository
            .findById(id)
            .map(existingManufacturerModel -> {
                Optional.ofNullable(makeModelRequest.model()).ifPresent(existingManufacturerModel::setModel);
                return existingManufacturerModel;
            })
            .map(makeModelRepository::save)
            .map(MakeModelResponseMapper.map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("No record found for id %d", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MakeModelResponse> findAll(Pageable pageable, String model) {
        Specification<MakeModel> specification = MakeModelSpec.withModel(model);
        return makeModelRepository.findAll(specification, pageable).map(MakeModelResponseMapper.map);
    }

    @Override
    @Transactional(readOnly = true)
    public MakeModelResponse findOne(Long id) {
        return makeModelRepository
            .findById(id)
            .map(MakeModelResponseMapper.map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("No record found for id %d", id)));
    }

    @Override
    public void delete(Long id) {
        makeModelRepository
            .findById(id)
            .ifPresentOrElse(makeModelRepository::delete, () -> {
                throw new RecordNotFoundException(String.format("No record found for id %d", id));
            });
    }
}
