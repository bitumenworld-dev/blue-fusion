package com.bitumen.bluefusion.service.storage;

import com.bitumen.bluefusion.service.storage.dto.StorageRequestDTO;
import com.bitumen.bluefusion.service.storage.dto.StorageResponseDTO;
import com.bitumen.bluefusion.service.storage.dto.StorageSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StorageService {
    StorageResponseDTO save(StorageRequestDTO storageRequestDTO);

    StorageResponseDTO update(Long storageId, StorageRequestDTO storageRequestDTO);

    void delete(Long storageId);

    StorageResponseDTO get(Long storageId);

    Page<StorageResponseDTO> getAll(Pageable pageable, StorageSearchDTO storageSearchDTO);
}
