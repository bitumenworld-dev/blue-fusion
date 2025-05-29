package com.bitumen.bluefusion.service.makeModelService.dto;

import com.bitumen.bluefusion.domain.MakeModel;
import java.util.function.Function;

public interface MakeModelResponseMapper {
    Function<MakeModel, MakeModelResponse> map = makeModel -> new MakeModelResponse(makeModel.getModelId(), makeModel.getModel());
}
