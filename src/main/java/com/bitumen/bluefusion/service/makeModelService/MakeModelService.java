package com.bitumen.bluefusion.service.makeModelService;

import com.bitumen.bluefusion.domain.MakeModel;
import com.bitumen.bluefusion.service.makeModelService.dto.MakeModelRequest;
import com.bitumen.bluefusion.service.makeModelService.dto.MakeModelResponse;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MakeModelService {
    MakeModelResponse save(MakeModelRequest makeModel);

    MakeModelResponse update(Long id, MakeModelRequest makeModel);

    MakeModelResponse partialUpdate(Long id, MakeModelRequest makeModel);

    Page<MakeModelResponse> findAll(Pageable pageable, String model);

    MakeModelResponse findOne(Long id);

    void delete(Long id);
}
