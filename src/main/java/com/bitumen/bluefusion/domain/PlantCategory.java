package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PlantCategory.
 */
@Entity
@Table(name = "plant_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlantCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "plant_category_id")
    private Long plantCategoryId;

    @Column(name = "plant_category_code")
    private String plantCategoryCode;

    @Column(name = "plant_category_name")
    private String plantCategoryName;

    @Column(name = "plant_category_description")
    private String plantCategoryDescription;

    @Column(name = "is_audited")
    private Boolean isAudited;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlantCategory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlantCategoryId() {
        return this.plantCategoryId;
    }

    public PlantCategory plantCategoryId(Long plantCategoryId) {
        this.setPlantCategoryId(plantCategoryId);
        return this;
    }

    public void setPlantCategoryId(Long plantCategoryId) {
        this.plantCategoryId = plantCategoryId;
    }

    public String getPlantCategoryCode() {
        return this.plantCategoryCode;
    }

    public PlantCategory plantCategoryCode(String plantCategoryCode) {
        this.setPlantCategoryCode(plantCategoryCode);
        return this;
    }

    public void setPlantCategoryCode(String plantCategoryCode) {
        this.plantCategoryCode = plantCategoryCode;
    }

    public String getPlantCategoryName() {
        return this.plantCategoryName;
    }

    public PlantCategory plantCategoryName(String plantCategoryName) {
        this.setPlantCategoryName(plantCategoryName);
        return this;
    }

    public void setPlantCategoryName(String plantCategoryName) {
        this.plantCategoryName = plantCategoryName;
    }

    public String getPlantCategoryDescription() {
        return this.plantCategoryDescription;
    }

    public PlantCategory plantCategoryDescription(String plantCategoryDescription) {
        this.setPlantCategoryDescription(plantCategoryDescription);
        return this;
    }

    public void setPlantCategoryDescription(String plantCategoryDescription) {
        this.plantCategoryDescription = plantCategoryDescription;
    }

    public Boolean getIsAudited() {
        return this.isAudited;
    }

    public PlantCategory isAudited(Boolean isAudited) {
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
        if (!(o instanceof PlantCategory)) {
            return false;
        }
        return getId() != null && getId().equals(((PlantCategory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlantCategory{" +
            "id=" + getId() +
            ", plantCategoryId=" + getPlantCategoryId() +
            ", plantCategoryCode='" + getPlantCategoryCode() + "'" +
            ", plantCategoryName='" + getPlantCategoryName() + "'" +
            ", plantCategoryDescription='" + getPlantCategoryDescription() + "'" +
            ", isAudited='" + getIsAudited() + "'" +
            "}";
    }
}
