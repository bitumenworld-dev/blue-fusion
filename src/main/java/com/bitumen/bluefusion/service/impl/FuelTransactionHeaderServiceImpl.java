package com.bitumen.bluefusion.service.impl;

import com.bitumen.bluefusion.domain.FuelTransactionHeader;
import com.bitumen.bluefusion.repository.FuelTransactionHeaderRepository;
import com.bitumen.bluefusion.service.FuelTransactionHeaderService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.FuelTransactionHeader}.
 */
@Service
@Transactional
public class FuelTransactionHeaderServiceImpl implements FuelTransactionHeaderService {

    private static final Logger LOG = LoggerFactory.getLogger(FuelTransactionHeaderServiceImpl.class);

    private final FuelTransactionHeaderRepository fuelTransactionHeaderRepository;

    public FuelTransactionHeaderServiceImpl(FuelTransactionHeaderRepository fuelTransactionHeaderRepository) {
        this.fuelTransactionHeaderRepository = fuelTransactionHeaderRepository;
    }

    @Override
    public FuelTransactionHeader save(FuelTransactionHeader fuelTransactionHeader) {
        LOG.debug("Request to save FuelTransactionHeader : {}", fuelTransactionHeader);
        return fuelTransactionHeaderRepository.save(fuelTransactionHeader);
    }

    @Override
    public FuelTransactionHeader update(FuelTransactionHeader fuelTransactionHeader) {
        LOG.debug("Request to update FuelTransactionHeader : {}", fuelTransactionHeader);
        return fuelTransactionHeaderRepository.save(fuelTransactionHeader);
    }

    @Override
    public Optional<FuelTransactionHeader> partialUpdate(FuelTransactionHeader fuelTransactionHeader) {
        LOG.debug("Request to partially update FuelTransactionHeader : {}", fuelTransactionHeader);

        return fuelTransactionHeaderRepository
            .findById(fuelTransactionHeader.getFuelTransactionHeaderId())
            .map(existingFuelTransactionHeader -> {
                if (fuelTransactionHeader.getFuelTransactionHeaderId() != null) {
                    existingFuelTransactionHeader.setFuelTransactionHeaderId(fuelTransactionHeader.getFuelTransactionHeaderId());
                }
                if (fuelTransactionHeader.getCompanyId() != null) {
                    existingFuelTransactionHeader.setCompanyId(fuelTransactionHeader.getCompanyId());
                }
                if (fuelTransactionHeader.getSupplierId() != null) {
                    existingFuelTransactionHeader.setSupplierId(fuelTransactionHeader.getSupplierId());
                }
                if (fuelTransactionHeader.getTransactionTypeId() != null) {
                    existingFuelTransactionHeader.setTransactionTypeId(fuelTransactionHeader.getTransactionTypeId());
                }
                if (fuelTransactionHeader.getFuelType() != null) {
                    existingFuelTransactionHeader.setFuelType(fuelTransactionHeader.getFuelType());
                }
                if (fuelTransactionHeader.getOrderNumber() != null) {
                    existingFuelTransactionHeader.setOrderNumber(fuelTransactionHeader.getOrderNumber());
                }
                if (fuelTransactionHeader.getDeliveryNote() != null) {
                    existingFuelTransactionHeader.setDeliveryNote(fuelTransactionHeader.getDeliveryNote());
                }
                if (fuelTransactionHeader.getGrvNumber() != null) {
                    existingFuelTransactionHeader.setGrvNumber(fuelTransactionHeader.getGrvNumber());
                }
                if (fuelTransactionHeader.getInvoiceNumber() != null) {
                    existingFuelTransactionHeader.setInvoiceNumber(fuelTransactionHeader.getInvoiceNumber());
                }
                if (fuelTransactionHeader.getPricePerLitre() != null) {
                    existingFuelTransactionHeader.setPricePerLitre(fuelTransactionHeader.getPricePerLitre());
                }
                if (fuelTransactionHeader.getNote() != null) {
                    existingFuelTransactionHeader.setNote(fuelTransactionHeader.getNote());
                }
                if (fuelTransactionHeader.getRegistrationNumber() != null) {
                    existingFuelTransactionHeader.setRegistrationNumber(fuelTransactionHeader.getRegistrationNumber());
                }
                if (fuelTransactionHeader.getAttendeeId() != null) {
                    existingFuelTransactionHeader.setAttendeeId(fuelTransactionHeader.getAttendeeId());
                }
                if (fuelTransactionHeader.getOperatorId() != null) {
                    existingFuelTransactionHeader.setOperatorId(fuelTransactionHeader.getOperatorId());
                }

                return existingFuelTransactionHeader;
            })
            .map(fuelTransactionHeaderRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FuelTransactionHeader> findAll(Pageable pageable) {
        LOG.debug("Request to get all FuelTransactionHeaders");
        return fuelTransactionHeaderRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FuelTransactionHeader> findOne(Long id) {
        LOG.debug("Request to get FuelTransactionHeader : {}", id);
        return fuelTransactionHeaderRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FuelTransactionHeader : {}", id);
        fuelTransactionHeaderRepository.deleteById(id);
    }
}
