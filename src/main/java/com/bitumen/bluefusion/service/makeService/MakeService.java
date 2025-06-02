package com.bitumen.bluefusion.service.makeService;

import com.bitumen.bluefusion.domain.Make;
import com.bitumen.bluefusion.service.makeService.dto.MakeRequest;
import com.bitumen.bluefusion.service.makeService.dto.MakeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Make}.
 */
public interface MakeService {
    /**
     * Save a make.
     *
     * @param make the entity to save.
     * @return the persisted entity.
     */
    MakeResponse save(MakeRequest make);

    /**
     * Updates a make.
     *
     * @param make the entity to update.
     * @return the persisted entity.
     */
    MakeResponse update(Long id, MakeRequest make);

    /**
     * Partially updates a make.
     *
     * @param make the entity to update partially.
     * @return the persisted entity.
     */
    MakeResponse partialUpdate(Long id, MakeRequest make);

    /**
     * Get all the manufacturers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MakeResponse> findAll(Pageable pageable, String make);

    /**
     * Get the "id" manufacturer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    MakeResponse findOne(Long id);

    /**
     * Delete the "id" manufacturer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
