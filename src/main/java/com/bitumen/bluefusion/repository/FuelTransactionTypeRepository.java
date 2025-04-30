package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.FuelTransactionType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FuelTransactionType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuelTransactionTypeRepository extends JpaRepository<FuelTransactionType, Long> {}
