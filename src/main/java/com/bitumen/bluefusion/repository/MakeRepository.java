package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.Make;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MakeRepository extends JpaRepository<Make, Long>, JpaSpecificationExecutor<Make> {
    List<Make> findByMakeContainingIgnoreCase(String makeName);
}
