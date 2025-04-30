package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FuelTransactionType.
 */
@Entity
@Table(name = "fuel_transaction_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuelTransactionType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fuel_transaction_type_id")
    private Long fuelTransactionTypeId;

    @Column(name = "fuel_transaction_type")
    private String fuelTransactionType;

    @Column(name = "is_active")
    private Boolean isActive;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FuelTransactionType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFuelTransactionTypeId() {
        return this.fuelTransactionTypeId;
    }

    public FuelTransactionType fuelTransactionTypeId(Long fuelTransactionTypeId) {
        this.setFuelTransactionTypeId(fuelTransactionTypeId);
        return this;
    }

    public void setFuelTransactionTypeId(Long fuelTransactionTypeId) {
        this.fuelTransactionTypeId = fuelTransactionTypeId;
    }

    public String getFuelTransactionType() {
        return this.fuelTransactionType;
    }

    public FuelTransactionType fuelTransactionType(String fuelTransactionType) {
        this.setFuelTransactionType(fuelTransactionType);
        return this;
    }

    public void setFuelTransactionType(String fuelTransactionType) {
        this.fuelTransactionType = fuelTransactionType;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public FuelTransactionType isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FuelTransactionType)) {
            return false;
        }
        return getId() != null && getId().equals(((FuelTransactionType) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuelTransactionType{" +
            "id=" + getId() +
            ", fuelTransactionTypeId=" + getFuelTransactionTypeId() +
            ", fuelTransactionType='" + getFuelTransactionType() + "'" +
            ", isActive='" + getIsActive() + "'" +
            "}";
    }
}
