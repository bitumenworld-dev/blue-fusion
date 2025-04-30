package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FuelPump.
 */
@Entity
@Table(name = "fuel_pump")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuelPump implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fuel_pump_id")
    private Long fuelPumpId;

    @Column(name = "fuel_pump_number")
    private String fuelPumpNumber;

    @Column(name = "current_storage_unit_id")
    private Long currentStorageUnitId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FuelPump id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFuelPumpId() {
        return this.fuelPumpId;
    }

    public FuelPump fuelPumpId(Long fuelPumpId) {
        this.setFuelPumpId(fuelPumpId);
        return this;
    }

    public void setFuelPumpId(Long fuelPumpId) {
        this.fuelPumpId = fuelPumpId;
    }

    public String getFuelPumpNumber() {
        return this.fuelPumpNumber;
    }

    public FuelPump fuelPumpNumber(String fuelPumpNumber) {
        this.setFuelPumpNumber(fuelPumpNumber);
        return this;
    }

    public void setFuelPumpNumber(String fuelPumpNumber) {
        this.fuelPumpNumber = fuelPumpNumber;
    }

    public Long getCurrentStorageUnitId() {
        return this.currentStorageUnitId;
    }

    public FuelPump currentStorageUnitId(Long currentStorageUnitId) {
        this.setCurrentStorageUnitId(currentStorageUnitId);
        return this;
    }

    public void setCurrentStorageUnitId(Long currentStorageUnitId) {
        this.currentStorageUnitId = currentStorageUnitId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FuelPump)) {
            return false;
        }
        return getId() != null && getId().equals(((FuelPump) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuelPump{" +
            "id=" + getId() +
            ", fuelPumpId=" + getFuelPumpId() +
            ", fuelPumpNumber='" + getFuelPumpNumber() + "'" +
            ", currentStorageUnitId=" + getCurrentStorageUnitId() +
            "}";
    }
}
