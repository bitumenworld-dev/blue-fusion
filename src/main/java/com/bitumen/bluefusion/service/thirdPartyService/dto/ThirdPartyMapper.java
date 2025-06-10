package com.bitumen.bluefusion.service.thirdPartyService.dto;

import com.bitumen.bluefusion.domain.ThirdParty;
import java.util.function.Function;

public interface ThirdPartyMapper {
    Function<ThirdParty, ThirdPartyResponse> map = thirdParty ->
        new ThirdPartyResponse(
            thirdParty.getThirdPartyId(),
            thirdParty.getThirdPartyName(),
            thirdParty.getThirdPartyShortName(),
            thirdParty.getIsActive(),
            thirdParty.getUsesFuelSystem()
        );
}
