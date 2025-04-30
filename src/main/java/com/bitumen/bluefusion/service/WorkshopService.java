package com.bitumen.bluefusion.service;

import com.bitumen.bluefusion.domain.Workshop;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bitumen.bluefusion.domain.Workshop}.
 */
public interface WorkshopService {
    /**
     * Save a workshop.
     *
     * @param workshop the entity to save.
     * @return the persisted entity.
     */
    Workshop save(Workshop workshop);

    /**
     * Updates a workshop.
     *
     * @param workshop the entity to update.
     * @return the persisted entity.
     */
    Workshop update(Workshop workshop);

    /**
     * Partially updates a workshop.
     *
     * @param workshop the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Workshop> partialUpdate(Workshop workshop);

    /**
     * Get all the workshops.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Workshop> findAll(Pageable pageable);

    /**
     * Get the "id" workshop.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Workshop> findOne(Long id);

    /**
     * Delete the "id" workshop.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
