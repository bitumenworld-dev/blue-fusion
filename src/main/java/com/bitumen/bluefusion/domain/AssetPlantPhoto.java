package com.bitumen.bluefusion.domain;

import com.bitumen.bluefusion.domain.enumeration.AssetPlantPhotoLabel;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AssetPlantPhoto.
 */
@Entity
@Table(name = "asset_plant_photo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssetPlantPhoto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "asset_plant_photo_id")
    private Long assetPlantPhotoId;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "asset_plant_id")
    private Long assetPlantId;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_plant_photo_label")
    private AssetPlantPhotoLabel assetPlantPhotoLabel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AssetPlantPhoto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssetPlantPhotoId() {
        return this.assetPlantPhotoId;
    }

    public AssetPlantPhoto assetPlantPhotoId(Long assetPlantPhotoId) {
        this.setAssetPlantPhotoId(assetPlantPhotoId);
        return this;
    }

    public void setAssetPlantPhotoId(Long assetPlantPhotoId) {
        this.assetPlantPhotoId = assetPlantPhotoId;
    }

    public String getName() {
        return this.name;
    }

    public AssetPlantPhoto name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return this.image;
    }

    public AssetPlantPhoto image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public AssetPlantPhoto imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Long getAssetPlantId() {
        return this.assetPlantId;
    }

    public AssetPlantPhoto assetPlantId(Long assetPlantId) {
        this.setAssetPlantId(assetPlantId);
        return this;
    }

    public void setAssetPlantId(Long assetPlantId) {
        this.assetPlantId = assetPlantId;
    }

    public AssetPlantPhotoLabel getAssetPlantPhotoLabel() {
        return this.assetPlantPhotoLabel;
    }

    public AssetPlantPhoto assetPlantPhotoLabel(AssetPlantPhotoLabel assetPlantPhotoLabel) {
        this.setAssetPlantPhotoLabel(assetPlantPhotoLabel);
        return this;
    }

    public void setAssetPlantPhotoLabel(AssetPlantPhotoLabel assetPlantPhotoLabel) {
        this.assetPlantPhotoLabel = assetPlantPhotoLabel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssetPlantPhoto)) {
            return false;
        }
        return getId() != null && getId().equals(((AssetPlantPhoto) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetPlantPhoto{" +
            "id=" + getId() +
            ", assetPlantPhotoId=" + getAssetPlantPhotoId() +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", assetPlantId=" + getAssetPlantId() +
            ", assetPlantPhotoLabel='" + getAssetPlantPhotoLabel() + "'" +
            "}";
    }
}
