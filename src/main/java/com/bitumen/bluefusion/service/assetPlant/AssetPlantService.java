package com.bitumen.bluefusion.service.assetPlant;

import com.bitumen.bluefusion.service.assetPlant.dto.AssetPlantFilterCriteria;
import com.bitumen.bluefusion.service.assetPlant.dto.AssetPlantRequest;
import com.bitumen.bluefusion.service.assetPlant.dto.AssetPlantResponse;
import jakarta.mail.MethodNotSupportedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssetPlantService {
    AssetPlantResponse save(AssetPlantRequest assetPlantRequest);

    AssetPlantResponse update(Long assetPlantId, AssetPlantRequest assetPlantRequest);

    AssetPlantResponse partialUpdate(Long id, AssetPlantRequest assetPlantRequest) throws MethodNotSupportedException;

    Page<AssetPlantResponse> findAll(Pageable pageable, AssetPlantFilterCriteria criteria);

    AssetPlantResponse findOne(Long id);

    AssetPlantResponse setCurrentOperator(Long assetPlantId, Long operatorId);

    AssetPlantResponse setAccessibleByCompany(Long assetPlantId, Long accessibleByCompanyId);

    AssetPlantResponse setCurrentContractDivision(Long assetPlantId, Long contractDivisionId);

    void delete(Long id);
}
