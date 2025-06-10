package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.FuelTransactionHeader;
import com.bitumen.bluefusion.domain.FuelTransactionLine;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelTransactionLineRepository
    extends JpaRepository<FuelTransactionLine, Long>, JpaSpecificationExecutor<FuelTransactionLine> {
    List<FuelTransactionLine> findByFuelTransactionHeader(FuelTransactionHeader fuelTransactionHeader);

    @Query(
        "SELECT SUM(f.litres * f.multiplier) FROM FuelTransactionLine f " +
        "JOIN f.fuelTransactionHeader h  WHERE h.storageUnit.storageId = :storageId"
    )
    BigDecimal getTotalFuelByStorageUnitId(@Param("storageId") Long storageId);
}
