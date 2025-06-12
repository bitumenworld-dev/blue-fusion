package com.bitumen.bluefusion.service.thirdPartyService;

import com.bitumen.bluefusion.service.thirdPartyService.dto.ThirdPartyRequest;
import com.bitumen.bluefusion.service.thirdPartyService.dto.ThirdPartyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ThirdPartyService {
    ThirdPartyResponse save(ThirdPartyRequest thirdPartyRequest);

    ThirdPartyResponse update(Long thirdPartyId, ThirdPartyRequest thirdPartyRequest);

    ThirdPartyResponse partialUpdate(Long thirdPartyId, ThirdPartyRequest thirdPartyRequest);

    Page<ThirdPartyResponse> findAll(Pageable pageable, String thirdPartyName, String thirdPartyShortName, Boolean isActive);

    ThirdPartyResponse findOne(Long thirdPartyId, ThirdPartyRequest thirdPartyRequest);

    void delete(Long thirdPartyId);
}
