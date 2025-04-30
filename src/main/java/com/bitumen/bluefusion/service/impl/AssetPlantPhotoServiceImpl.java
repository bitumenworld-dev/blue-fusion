package com.bitumen.bluefusion.service.impl;

import com.bitumen.bluefusion.domain.AssetPlantPhoto;
import com.bitumen.bluefusion.repository.AssetPlantPhotoRepository;
import com.bitumen.bluefusion.service.AssetPlantPhotoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.AssetPlantPhoto}.
 */
@Service
@Transactional
public class AssetPlantPhotoServiceImpl implements AssetPlantPhotoService {

    private static final Logger LOG = LoggerFactory.getLogger(AssetPlantPhotoServiceImpl.class);

    private final AssetPlantPhotoRepository assetPlantPhotoRepository;

    public AssetPlantPhotoServiceImpl(AssetPlantPhotoRepository assetPlantPhotoRepository) {
        this.assetPlantPhotoRepository = assetPlantPhotoRepository;
    }

    @Override
    public AssetPlantPhoto save(AssetPlantPhoto assetPlantPhoto) {
        LOG.debug("Request to save AssetPlantPhoto : {}", assetPlantPhoto);
        return assetPlantPhotoRepository.save(assetPlantPhoto);
    }

    @Override
    public AssetPlantPhoto update(AssetPlantPhoto assetPlantPhoto) {
        LOG.debug("Request to update AssetPlantPhoto : {}", assetPlantPhoto);
        return assetPlantPhotoRepository.save(assetPlantPhoto);
    }

    @Override
    public Optional<AssetPlantPhoto> partialUpdate(AssetPlantPhoto assetPlantPhoto) {
        LOG.debug("Request to partially update AssetPlantPhoto : {}", assetPlantPhoto);

        return assetPlantPhotoRepository
            .findById(assetPlantPhoto.getId())
            .map(existingAssetPlantPhoto -> {
                if (assetPlantPhoto.getAssetPlantPhotoId() != null) {
                    existingAssetPlantPhoto.setAssetPlantPhotoId(assetPlantPhoto.getAssetPlantPhotoId());
                }
                if (assetPlantPhoto.getName() != null) {
                    existingAssetPlantPhoto.setName(assetPlantPhoto.getName());
                }
                if (assetPlantPhoto.getImage() != null) {
                    existingAssetPlantPhoto.setImage(assetPlantPhoto.getImage());
                }
                if (assetPlantPhoto.getImageContentType() != null) {
                    existingAssetPlantPhoto.setImageContentType(assetPlantPhoto.getImageContentType());
                }
                if (assetPlantPhoto.getAssetPlantId() != null) {
                    existingAssetPlantPhoto.setAssetPlantId(assetPlantPhoto.getAssetPlantId());
                }
                if (assetPlantPhoto.getAssetPlantPhotoLabel() != null) {
                    existingAssetPlantPhoto.setAssetPlantPhotoLabel(assetPlantPhoto.getAssetPlantPhotoLabel());
                }

                return existingAssetPlantPhoto;
            })
            .map(assetPlantPhotoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AssetPlantPhoto> findAll(Pageable pageable) {
        LOG.debug("Request to get all AssetPlantPhotos");
        return assetPlantPhotoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AssetPlantPhoto> findOne(Long id) {
        LOG.debug("Request to get AssetPlantPhoto : {}", id);
        return assetPlantPhotoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete AssetPlantPhoto : {}", id);
        assetPlantPhotoRepository.deleteById(id);
    }
}
