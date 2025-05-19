package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.Company;
import com.bitumen.bluefusion.domain.Site;
import com.bitumen.bluefusion.domain.Storage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long>, JpaSpecificationExecutor<Storage> {
    List<Storage> findBySite(Site site);

    List<Storage> findByIsFixed(Boolean isFixed);

    List<Storage> findByCompany(Company company);
}
