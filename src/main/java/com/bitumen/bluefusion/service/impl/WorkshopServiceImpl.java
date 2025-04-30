package com.bitumen.bluefusion.service.impl;

import com.bitumen.bluefusion.domain.Workshop;
import com.bitumen.bluefusion.repository.WorkshopRepository;
import com.bitumen.bluefusion.service.WorkshopService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.Workshop}.
 */
@Service
@Transactional
public class WorkshopServiceImpl implements WorkshopService {

    private static final Logger LOG = LoggerFactory.getLogger(WorkshopServiceImpl.class);

    private final WorkshopRepository workshopRepository;

    public WorkshopServiceImpl(WorkshopRepository workshopRepository) {
        this.workshopRepository = workshopRepository;
    }

    @Override
    public Workshop save(Workshop workshop) {
        LOG.debug("Request to save Workshop : {}", workshop);
        return workshopRepository.save(workshop);
    }

    @Override
    public Workshop update(Workshop workshop) {
        LOG.debug("Request to update Workshop : {}", workshop);
        return workshopRepository.save(workshop);
    }

    @Override
    public Optional<Workshop> partialUpdate(Workshop workshop) {
        LOG.debug("Request to partially update Workshop : {}", workshop);

        return workshopRepository
            .findById(workshop.getId())
            .map(existingWorkshop -> {
                if (workshop.getWorkshopId() != null) {
                    existingWorkshop.setWorkshopId(workshop.getWorkshopId());
                }
                if (workshop.getSiteId() != null) {
                    existingWorkshop.setSiteId(workshop.getSiteId());
                }
                if (workshop.getWorkshopName() != null) {
                    existingWorkshop.setWorkshopName(workshop.getWorkshopName());
                }

                return existingWorkshop;
            })
            .map(workshopRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Workshop> findAll(Pageable pageable) {
        LOG.debug("Request to get all Workshops");
        return workshopRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Workshop> findOne(Long id) {
        LOG.debug("Request to get Workshop : {}", id);
        return workshopRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Workshop : {}", id);
        workshopRepository.deleteById(id);
    }
}
