package com.bitumen.bluefusion.service.companyService;

import com.bitumen.bluefusion.service.companyService.dto.CompanyRequest;
import com.bitumen.bluefusion.service.companyService.dto.CompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bitumen.bluefusion.domain.Company}.
 */
public interface CompanyService {
    /**
     * Save a company.
     *
     * @param company the entity to save.
     * @return the persisted entity.
     */
    CompanyResponse save(CompanyRequest company);

    /**
     * Updates a company.
     *
     * @param company the entity to update.
     * @return the persisted entity.
     */
    CompanyResponse update(Long companyId, CompanyRequest company);

    /**
     * Partially updates a company.
     *
     * @param company the entity to update partially.
     * @return the persisted entity.
     */
    CompanyResponse partialUpdate(Long companyId, CompanyRequest company);

    /**
     * Get all the companies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompanyResponse> findAll(Pageable pageable, String companyName, Boolean usesFuelSystem, Boolean isActive);

    /**
     * Get the "id" company.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    CompanyResponse findByCompanyId(Long id);

    /**
     * Delete the "id" company.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
