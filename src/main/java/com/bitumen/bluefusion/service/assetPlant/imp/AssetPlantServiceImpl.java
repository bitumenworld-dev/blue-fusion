package com.bitumen.bluefusion.service.assetPlant.imp;

import com.bitumen.bluefusion.domain.AssetPlant;
import com.bitumen.bluefusion.repository.AssetPlantRepository;
import com.bitumen.bluefusion.service.assetPlant.AssetPlantService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.AssetPlant}.
 */
@Service
@Transactional
public class AssetPlantServiceImpl implements AssetPlantService {

    private static final Logger LOG = LoggerFactory.getLogger(AssetPlantServiceImpl.class);

    private final AssetPlantRepository assetPlantRepository;

    public AssetPlantServiceImpl(AssetPlantRepository assetPlantRepository) {
        this.assetPlantRepository = assetPlantRepository;
    }

    @Override
    public AssetPlant save(AssetPlant assetPlant) {
        LOG.debug("Request to save AssetPlant : {}", assetPlant);
        return assetPlantRepository.save(assetPlant);
    }

    @Override
    public AssetPlant update(AssetPlant assetPlant) {
        LOG.debug("Request to update AssetPlant : {}", assetPlant);
        return assetPlantRepository.save(assetPlant);
    }

    @Override
    public Optional<AssetPlant> partialUpdate(AssetPlant assetPlant) {
        LOG.debug("Request to partially update AssetPlant : {}", assetPlant);

        //        return assetPlantRepository
        //            .findByAssetPlantId(assetPlant.getAssetPlantId())
        //            .map(existingAssetPlant -> {
        //                if (assetPlant.getAssetPlantId() != null) {
        //                    existingAssetPlant.setAssetPlantId(assetPlant.getAssetPlantId());
        //                }
        //                if (assetPlant.getFleetNumber() != null) {
        //                    existingAssetPlant.setFleetNumber(assetPlant.getFleetNumber());
        //                }
        //                if (assetPlant.getNumberPlate() != null) {
        //                    existingAssetPlant.setNumberPlate(assetPlant.getNumberPlate());
        //                }
        //                if (assetPlant.getFleetDescription() != null) {
        //                    existingAssetPlant.setFleetDescription(assetPlant.getFleetDescription());
        //                }
        //                if (assetPlant.getOwnerId() != null) {
        //                    existingAssetPlant.setOwnerId(assetPlant.getOwnerId());
        //                }
        //                if (assetPlant.getAccessibleByCompany() != null) {
        //                    existingAssetPlant.setAccessibleByCompany(assetPlant.getAccessibleByCompany());
        //                }
        //                if (assetPlant.getDriverOrOperator() != null) {
        //                    existingAssetPlant.setDriverOrOperator(assetPlant.getDriverOrOperator());
        //                }
        //                if (assetPlant.getPlantCategoryId() != null) {
        //                    existingAssetPlant.setPlantCategoryId(assetPlant.getPlantCategoryId());
        //                }
        //                if (assetPlant.getPlantSubcategoryId() != null) {
        //                    existingAssetPlant.setPlantSubcategoryId(assetPlant.getPlantSubcategoryId());
        //                }
        //                if (assetPlant.getManufacturerId() != null) {
        //                    existingAssetPlant.setManufacturerId(assetPlant.getManufacturerId());
        //                }
        //                if (assetPlant.getModelId() != null) {
        //                    existingAssetPlant.setModelId(assetPlant.getModelId());
        //                }
        //                if (assetPlant.getYearOfManufacture() != null) {
        //                    existingAssetPlant.setYearOfManufacture(assetPlant.getYearOfManufacture());
        //                }
        //                if (assetPlant.getColour() != null) {
        //                    existingAssetPlant.setColour(assetPlant.getColour());
        //                }
        //                if (assetPlant.getHorseOrTrailer() != null) {
        //                    existingAssetPlant.setHorseOrTrailer(assetPlant.getHorseOrTrailer());
        //                }
        //                if (assetPlant.getSmrReaderType() != null) {
        //                    existingAssetPlant.setSmrReaderType(assetPlant.getSmrReaderType());
        //                }
        //                if (assetPlant.getCurrentSmrIndex() != null) {
        //                    existingAssetPlant.setCurrentSmrIndex(assetPlant.getCurrentSmrIndex());
        //                }
        //                if (assetPlant.getEngineNumber() != null) {
        //                    existingAssetPlant.setEngineNumber(assetPlant.getEngineNumber());
        //                }
        //                if (assetPlant.getEngineCapacityCc() != null) {
        //                    existingAssetPlant.setEngineCapacityCc(assetPlant.getEngineCapacityCc());
        //                }
        //                if (assetPlant.getCurrentSiteId() != null) {
        //                    existingAssetPlant.setCurrentSiteId(assetPlant.getCurrentSiteId());
        //                }
        //                if (assetPlant.getCurrentContractId() != null) {
        //                    existingAssetPlant.setCurrentContractId(assetPlant.getCurrentContractId());
        //                }
        //                if (assetPlant.getCurrentOperatorId() != null) {
        //                    existingAssetPlant.setCurrentOperatorId(assetPlant.getCurrentOperatorId());
        //                }
        //                if (assetPlant.getLedgerCode() != null) {
        //                    existingAssetPlant.setLedgerCode(assetPlant.getLedgerCode());
        //                }
        //                if (assetPlant.getFuelType() != null) {
        //                    existingAssetPlant.setFuelType(assetPlant.getFuelType());
        //                }
        //                if (assetPlant.getTankCapacityLitres() != null) {
        //                    existingAssetPlant.setTankCapacityLitres(assetPlant.getTankCapacityLitres());
        //                }
        //                if (assetPlant.getConsumptionUnit() != null) {
        //                    existingAssetPlant.setConsumptionUnit(assetPlant.getConsumptionUnit());
        //                }
        //                if (assetPlant.getPlantHoursStatus() != null) {
        //                    existingAssetPlant.setPlantHoursStatus(assetPlant.getPlantHoursStatus());
        //                }
        //                if (assetPlant.getPrimeMover() != null) {
        //                    existingAssetPlant.setPrimeMover(assetPlant.getPrimeMover());
        //                }
        //                if (assetPlant.getCapacityTons() != null) {
        //                    existingAssetPlant.setCapacityTons(assetPlant.getCapacityTons());
        //                }
        //                if (assetPlant.getCapacityM3Loose() != null) {
        //                    existingAssetPlant.setCapacityM3Loose(assetPlant.getCapacityM3Loose());
        //                }
        //                if (assetPlant.getCapacityM3Tight() != null) {
        //                    existingAssetPlant.setCapacityM3Tight(assetPlant.getCapacityM3Tight());
        //                }
        //                if (assetPlant.getMaximumConsumption() != null) {
        //                    existingAssetPlant.setMaximumConsumption(assetPlant.getMaximumConsumption());
        //                }
        //                if (assetPlant.getMinimumConsumption() != null) {
        //                    existingAssetPlant.setMinimumConsumption(assetPlant.getMinimumConsumption());
        //                }
        //                if (assetPlant.getMaximumSmrOnFullTank() != null) {
        //                    existingAssetPlant.setMaximumSmrOnFullTank(assetPlant.getMaximumSmrOnFullTank());
        //                }
        //                if (assetPlant.getTrackConsumption() != null) {
        //                    existingAssetPlant.setTrackConsumption(assetPlant.getTrackConsumption());
        //                }
        //                if (assetPlant.getTrackSmrReading() != null) {
        //                    existingAssetPlant.setTrackSmrReading(assetPlant.getTrackSmrReading());
        //                }
        //                if (assetPlant.getTrackService() != null) {
        //                    existingAssetPlant.setTrackService(assetPlant.getTrackService());
        //                }
        //                if (assetPlant.getDeleted() != null) {
        //                    existingAssetPlant.setDeleted(assetPlant.getDeleted());
        //                }
        //                if (assetPlant.getRequestWeeklyMileage() != null) {
        //                    existingAssetPlant.setRequestWeeklyMileage(assetPlant.getRequestWeeklyMileage());
        //                }
        //                if (assetPlant.getSent() != null) {
        //                    existingAssetPlant.setSent(assetPlant.getSent());
        //                }
        //                if (assetPlant.getChassisNumber() != null) {
        //                    existingAssetPlant.setChassisNumber(assetPlant.getChassisNumber());
        //                }
        //                if (assetPlant.getCurrentLocation() != null) {
        //                    existingAssetPlant.setCurrentLocation(assetPlant.getCurrentLocation());
        //                }
        //
        //                return existingAssetPlant;
        //            })
        //            .map(assetPlantRepository::save);

        return Optional.of(new AssetPlant());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AssetPlant> findAll(Pageable pageable) {
        LOG.debug("Request to get all AssetPlants");
        return assetPlantRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AssetPlant> findOne(Long id) {
        LOG.debug("Request to get AssetPlant : {}", id);
        return assetPlantRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete AssetPlant : {}", id);
        assetPlantRepository.deleteById(id);
    }
}
