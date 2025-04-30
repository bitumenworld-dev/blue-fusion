package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.FuelTransactionHeader;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FuelTransactionHeader entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuelTransactionHeaderRepository extends JpaRepository<FuelTransactionHeader, Long> {}
