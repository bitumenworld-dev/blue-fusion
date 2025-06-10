package com.bitumen.bluefusion.service.thirdPartyService;

import com.bitumen.bluefusion.service.thirdPartyService.dto.ThirdPartyRequest;
import com.bitumen.bluefusion.service.thirdPartyService.dto.ThirdPartyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface ThirdPartyService {
    ThirdPartyResponse save(ThirdPartyRequest thirdPartyRequest);

    ThirdPartyResponse update(Long thirdPartyId, ThirdPartyRequest thirdPartyRequest);

    ThirdPartyResponse partialUpdate(Long thirdPartyId, ThirdPartyRequest thirdPartyRequest);

    @Transactional(readOnly = true)
    Page<ThirdPartyResponse> findAll(Pageable pageable, String thirdPartyName, String thirdPartyShortName, Boolean isActive);

    @Transactional(readOnly = true)
    ThirdPartyResponse findOne(Long thirdPartyId, ThirdPartyRequest thirdPartyRequest);

    void delete(Long thirdPartyId);
}
