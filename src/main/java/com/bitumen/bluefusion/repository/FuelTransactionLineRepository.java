package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.FuelTransactionLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FuelTransactionLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuelTransactionLineRepository extends JpaRepository<FuelTransactionLine, Long> {}
