package com.bitumen.bluefusion.domain;

import com.bitumen.bluefusion.domain.enumeration.ServiceUnit;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AssetPlantServiceReading.
 */
@Entity
@Table(name = "asset_plant_service_reading")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssetPlantServiceReading implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "asset_plant_service_reading_id")
    private Long assetPlantServiceReadingId;

    @Column(name = "asset_plant_id")
    private Long assetPlantId;

    @Column(name = "next_service_smr_reading")
    private Float nextServiceSmrReading;

    @Column(name = "estimated_units_per_day")
    private Float estimatedUnitsPerDay;

    @Column(name = "estimated_next_service_date")
    private LocalDate estimatedNextServiceDate;

    @Column(name = "latest_smr_readings")
    private Float latestSmrReadings;

    @Column(name = "service_interval")
    private Float serviceInterval;

    @Column(name = "last_service_date")
    private LocalDate lastServiceDate;

    @Column(name = "latest_smr_date")
    private LocalDate latestSmrDate;

    @Column(name = "last_service_smr")
    private Float lastServiceSmr;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_unit")
    private ServiceUnit serviceUnit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AssetPlantServiceReading id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssetPlantServiceReadingId() {
        return this.assetPlantServiceReadingId;
    }

    public AssetPlantServiceReading assetPlantServiceReadingId(Long assetPlantServiceReadingId) {
        this.setAssetPlantServiceReadingId(assetPlantServiceReadingId);
        return this;
    }

    public void setAssetPlantServiceReadingId(Long assetPlantServiceReadingId) {
        this.assetPlantServiceReadingId = assetPlantServiceReadingId;
    }

    public Long getAssetPlantId() {
        return this.assetPlantId;
    }

    public AssetPlantServiceReading assetPlantId(Long assetPlantId) {
        this.setAssetPlantId(assetPlantId);
        return this;
    }

    public void setAssetPlantId(Long assetPlantId) {
        this.assetPlantId = assetPlantId;
    }

    public Float getNextServiceSmrReading() {
        return this.nextServiceSmrReading;
    }

    public AssetPlantServiceReading nextServiceSmrReading(Float nextServiceSmrReading) {
        this.setNextServiceSmrReading(nextServiceSmrReading);
        return this;
    }

    public void setNextServiceSmrReading(Float nextServiceSmrReading) {
        this.nextServiceSmrReading = nextServiceSmrReading;
    }

    public Float getEstimatedUnitsPerDay() {
        return this.estimatedUnitsPerDay;
    }

    public AssetPlantServiceReading estimatedUnitsPerDay(Float estimatedUnitsPerDay) {
        this.setEstimatedUnitsPerDay(estimatedUnitsPerDay);
        return this;
    }

    public void setEstimatedUnitsPerDay(Float estimatedUnitsPerDay) {
        this.estimatedUnitsPerDay = estimatedUnitsPerDay;
    }

    public LocalDate getEstimatedNextServiceDate() {
        return this.estimatedNextServiceDate;
    }

    public AssetPlantServiceReading estimatedNextServiceDate(LocalDate estimatedNextServiceDate) {
        this.setEstimatedNextServiceDate(estimatedNextServiceDate);
        return this;
    }

    public void setEstimatedNextServiceDate(LocalDate estimatedNextServiceDate) {
        this.estimatedNextServiceDate = estimatedNextServiceDate;
    }

    public Float getLatestSmrReadings() {
        return this.latestSmrReadings;
    }

    public AssetPlantServiceReading latestSmrReadings(Float latestSmrReadings) {
        this.setLatestSmrReadings(latestSmrReadings);
        return this;
    }

    public void setLatestSmrReadings(Float latestSmrReadings) {
        this.latestSmrReadings = latestSmrReadings;
    }

    public Float getServiceInterval() {
        return this.serviceInterval;
    }

    public AssetPlantServiceReading serviceInterval(Float serviceInterval) {
        this.setServiceInterval(serviceInterval);
        return this;
    }

    public void setServiceInterval(Float serviceInterval) {
        this.serviceInterval = serviceInterval;
    }

    public LocalDate getLastServiceDate() {
        return this.lastServiceDate;
    }

    public AssetPlantServiceReading lastServiceDate(LocalDate lastServiceDate) {
        this.setLastServiceDate(lastServiceDate);
        return this;
    }

    public void setLastServiceDate(LocalDate lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }

    public LocalDate getLatestSmrDate() {
        return this.latestSmrDate;
    }

    public AssetPlantServiceReading latestSmrDate(LocalDate latestSmrDate) {
        this.setLatestSmrDate(latestSmrDate);
        return this;
    }

    public void setLatestSmrDate(LocalDate latestSmrDate) {
        this.latestSmrDate = latestSmrDate;
    }

    public Float getLastServiceSmr() {
        return this.lastServiceSmr;
    }

    public AssetPlantServiceReading lastServiceSmr(Float lastServiceSmr) {
        this.setLastServiceSmr(lastServiceSmr);
        return this;
    }

    public void setLastServiceSmr(Float lastServiceSmr) {
        this.lastServiceSmr = lastServiceSmr;
    }

    public ServiceUnit getServiceUnit() {
        return this.serviceUnit;
    }

    public AssetPlantServiceReading serviceUnit(ServiceUnit serviceUnit) {
        this.setServiceUnit(serviceUnit);
        return this;
    }

    public void setServiceUnit(ServiceUnit serviceUnit) {
        this.serviceUnit = serviceUnit;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssetPlantServiceReading)) {
            return false;
        }
        return getId() != null && getId().equals(((AssetPlantServiceReading) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetPlantServiceReading{" +
            "id=" + getId() +
            ", assetPlantServiceReadingId=" + getAssetPlantServiceReadingId() +
            ", assetPlantId=" + getAssetPlantId() +
            ", nextServiceSmrReading=" + getNextServiceSmrReading() +
            ", estimatedUnitsPerDay=" + getEstimatedUnitsPerDay() +
            ", estimatedNextServiceDate='" + getEstimatedNextServiceDate() + "'" +
            ", latestSmrReadings=" + getLatestSmrReadings() +
            ", serviceInterval=" + getServiceInterval() +
            ", lastServiceDate='" + getLastServiceDate() + "'" +
            ", latestSmrDate='" + getLatestSmrDate() + "'" +
            ", lastServiceSmr=" + getLastServiceSmr() +
            ", serviceUnit='" + getServiceUnit() + "'" +
            "}";
    }
}
