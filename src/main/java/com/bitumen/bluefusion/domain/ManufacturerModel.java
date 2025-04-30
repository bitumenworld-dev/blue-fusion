package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ManufacturerModel.
 */
@Entity
@Table(name = "manufacturer_model")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ManufacturerModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "model_id")
    private Long modelId;

    @Column(name = "model_name")
    private String modelName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ManufacturerModel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModelId() {
        return this.modelId;
    }

    public ManufacturerModel modelId(Long modelId) {
        this.setModelId(modelId);
        return this;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return this.modelName;
    }

    public ManufacturerModel modelName(String modelName) {
        this.setModelName(modelName);
        return this;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ManufacturerModel)) {
            return false;
        }
        return getId() != null && getId().equals(((ManufacturerModel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ManufacturerModel{" +
            "id=" + getId() +
            ", modelId=" + getModelId() +
            ", modelName='" + getModelName() + "'" +
            "}";
    }
}
