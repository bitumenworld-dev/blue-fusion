package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long>, JpaSpecificationExecutor<Storage> {
    Boolean existsByStorageId(Long storageId);
}
