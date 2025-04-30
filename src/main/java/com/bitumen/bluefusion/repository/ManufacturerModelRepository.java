package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.ManufacturerModel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ManufacturerModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ManufacturerModelRepository extends JpaRepository<ManufacturerModel, Long> {}
