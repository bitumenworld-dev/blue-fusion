package com.bitumen.bluefusion.service.makeService.dto;

import com.bitumen.bluefusion.domain.Make;
import java.util.function.Function;

public interface MakeResponseMapper {
    Function<Make, MakeResponse> map = make -> new MakeResponse(make.getMakeId(), make.getMake());
}
