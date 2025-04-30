package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PlantSubcategory.
 */
@Entity
@Table(name = "plant_subcategory")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlantSubcategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "plant_subcategory_id")
    private Long plantSubcategoryId;

    @Column(name = "plant_subcategory_code")
    private String plantSubcategoryCode;

    @Column(name = "plant_subcategory_name")
    private String plantSubcategoryName;

    @Column(name = "plant_subcategory_description")
    private String plantSubcategoryDescription;

    @Column(name = "is_audited")
    private Boolean isAudited;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlantSubcategory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlantSubcategoryId() {
        return this.plantSubcategoryId;
    }

    public PlantSubcategory plantSubcategoryId(Long plantSubcategoryId) {
        this.setPlantSubcategoryId(plantSubcategoryId);
        return this;
    }

    public void setPlantSubcategoryId(Long plantSubcategoryId) {
        this.plantSubcategoryId = plantSubcategoryId;
    }

    public String getPlantSubcategoryCode() {
        return this.plantSubcategoryCode;
    }

    public PlantSubcategory plantSubcategoryCode(String plantSubcategoryCode) {
        this.setPlantSubcategoryCode(plantSubcategoryCode);
        return this;
    }

    public void setPlantSubcategoryCode(String plantSubcategoryCode) {
        this.plantSubcategoryCode = plantSubcategoryCode;
    }

    public String getPlantSubcategoryName() {
        return this.plantSubcategoryName;
    }

    public PlantSubcategory plantSubcategoryName(String plantSubcategoryName) {
        this.setPlantSubcategoryName(plantSubcategoryName);
        return this;
    }

    public void setPlantSubcategoryName(String plantSubcategoryName) {
        this.plantSubcategoryName = plantSubcategoryName;
    }

    public String getPlantSubcategoryDescription() {
        return this.plantSubcategoryDescription;
    }

    public PlantSubcategory plantSubcategoryDescription(String plantSubcategoryDescription) {
        this.setPlantSubcategoryDescription(plantSubcategoryDescription);
        return this;
    }

    public void setPlantSubcategoryDescription(String plantSubcategoryDescription) {
        this.plantSubcategoryDescription = plantSubcategoryDescription;
    }

    public Boolean getIsAudited() {
        return this.isAudited;
    }

    public PlantSubcategory isAudited(Boolean isAudited) {
        this.setIsAudited(isAudited);
        return this;
    }

    public void setIsAudited(Boolean isAudited) {
        this.isAudited = isAudited;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlantSubcategory)) {
            return false;
        }
        return getId() != null && getId().equals(((PlantSubcategory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlantSubcategory{" +
            "id=" + getId() +
            ", plantSubcategoryId=" + getPlantSubcategoryId() +
            ", plantSubcategoryCode='" + getPlantSubcategoryCode() + "'" +
            ", plantSubcategoryName='" + getPlantSubcategoryName() + "'" +
            ", plantSubcategoryDescription='" + getPlantSubcategoryDescription() + "'" +
            ", isAudited='" + getIsAudited() + "'" +
            "}";
    }
}
