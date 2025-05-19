package com.bitumen.bluefusion.service.impl;

import com.bitumen.bluefusion.domain.FuelTransactionLine;
import com.bitumen.bluefusion.repository.FuelTransactionLineRepository;
import com.bitumen.bluefusion.service.FuelTransactionLineService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.FuelTransactionLine}.
 */
@Service
@Transactional
public class FuelTransactionLineServiceImpl implements FuelTransactionLineService {

    private static final Logger LOG = LoggerFactory.getLogger(FuelTransactionLineServiceImpl.class);

    private final FuelTransactionLineRepository fuelTransactionLineRepository;

    public FuelTransactionLineServiceImpl(FuelTransactionLineRepository fuelTransactionLineRepository) {
        this.fuelTransactionLineRepository = fuelTransactionLineRepository;
    }

    @Override
    public FuelTransactionLine save(FuelTransactionLine fuelTransactionLine) {
        LOG.debug("Request to save FuelTransactionLine : {}", fuelTransactionLine);
        return fuelTransactionLineRepository.save(fuelTransactionLine);
    }

    @Override
    public FuelTransactionLine update(FuelTransactionLine fuelTransactionLine) {
        LOG.debug("Request to update FuelTransactionLine : {}", fuelTransactionLine);
        return fuelTransactionLineRepository.save(fuelTransactionLine);
    }

    @Override
    public Optional<FuelTransactionLine> partialUpdate(FuelTransactionLine fuelTransactionLine) {
        LOG.debug("Request to partially update FuelTransactionLine : {}", fuelTransactionLine);

        //        return fuelTransactionLineRepository
        //            .findById(fuelTransactionLine.getFuelTransactionLineId())
        //            .map(existingFuelTransactionLine -> {
        //                if (fuelTransactionLine.getFuelTransactionLineId() != null) {
        //                    existingFuelTransactionLine.setFuelTransactionLineId(fuelTransactionLine.getFuelTransactionLineId());
        //                }
        //                if (fuelTransactionLine.getFuelTransactionHeaderId() != null) {
        //                    existingFuelTransactionLine.setFuelTransactionHeaderId(fuelTransactionLine.getFuelTransactionHeaderId());
        //                }
        //                if (fuelTransactionLine.getAssetPlantId() != null) {
        //                    existingFuelTransactionLine.setAssetPlantId(fuelTransactionLine.getAssetPlantId());
        //                }
        //                if (fuelTransactionLine.getContractDivisionId() != null) {
        //                    existingFuelTransactionLine.setContractDivisionId(fuelTransactionLine.getContractDivisionId());
        //                }
        //                if (fuelTransactionLine.getIssuanceTypeId() != null) {
        //                    existingFuelTransactionLine.setIssuanceTypeId(fuelTransactionLine.getIssuanceTypeId());
        //                }
        //                if (fuelTransactionLine.getPumpId() != null) {
        //                    existingFuelTransactionLine.setPumpId(fuelTransactionLine.getPumpId());
        //                }
        //                if (fuelTransactionLine.getStorageUnitId() != null) {
        //                    existingFuelTransactionLine.setStorageUnitId(fuelTransactionLine.getStorageUnitId());
        //                }
        //                if (fuelTransactionLine.getLitres() != null) {
        //                    existingFuelTransactionLine.setLitres(fuelTransactionLine.getLitres());
        //                }
        //                if (fuelTransactionLine.getMeterReadingStart() != null) {
        //                    existingFuelTransactionLine.setMeterReadingStart(fuelTransactionLine.getMeterReadingStart());
        //                }
        //                if (fuelTransactionLine.getMeterReadingEnd() != null) {
        //                    existingFuelTransactionLine.setMeterReadingEnd(fuelTransactionLine.getMeterReadingEnd());
        //                }
        //                if (fuelTransactionLine.getMultiplier() != null) {
        //                    existingFuelTransactionLine.setMultiplier(fuelTransactionLine.getMultiplier());
        //                }
        //
        //                return existingFuelTransactionLine;
        //            })
        //            .map(fuelTransactionLineRepository::save);
        return Optional.of(new FuelTransactionLine());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FuelTransactionLine> findAll(Pageable pageable) {
        LOG.debug("Request to get all FuelTransactionLines");
        return fuelTransactionLineRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FuelTransactionLine> findOne(Long id) {
        LOG.debug("Request to get FuelTransactionLine : {}", id);
        return fuelTransactionLineRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FuelTransactionLine : {}", id);
        fuelTransactionLineRepository.deleteById(id);
    }
}
