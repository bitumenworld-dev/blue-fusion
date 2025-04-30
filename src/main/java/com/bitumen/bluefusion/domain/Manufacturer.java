package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Manufacturer.
 */
@Entity
@Table(name = "manufacturer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Manufacturer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "manufacturer_id")
    private Long manufacturerId;

    @Column(name = "manufacturer_name")
    private String manufacturerName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Manufacturer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getManufacturerId() {
        return this.manufacturerId;
    }

    public Manufacturer manufacturerId(Long manufacturerId) {
        this.setManufacturerId(manufacturerId);
        return this;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerName() {
        return this.manufacturerName;
    }

    public Manufacturer manufacturerName(String manufacturerName) {
        this.setManufacturerName(manufacturerName);
        return this;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Manufacturer)) {
            return false;
        }
        return getId() != null && getId().equals(((Manufacturer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Manufacturer{" +
            "id=" + getId() +
            ", manufacturerId=" + getManufacturerId() +
            ", manufacturerName='" + getManufacturerName() + "'" +
            "}";
    }
}
