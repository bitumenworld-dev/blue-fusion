package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.FuelTransactionHeader;
import com.bitumen.bluefusion.domain.FuelTransactionLine;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelTransactionLineRepository
    extends JpaRepository<FuelTransactionLine, Long>, JpaSpecificationExecutor<FuelTransactionLine> {
    List<FuelTransactionLine> findByFuelTransactionHeader(FuelTransactionHeader fuelTransactionHeader);
}
