package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FuelTransactionLine.
 */
@Entity
@Table(name = "fuel_transaction_line")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuelTransactionLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fuel_transaction_line_id")
    private Long fuelTransactionLineId;

    @Column(name = "fuel_transaction_header_id")
    private Long fuelTransactionHeaderId;

    @Column(name = "asset_plant_id")
    private Long assetPlantId;

    @Column(name = "contract_division_id")
    private Long contractDivisionId;

    @Column(name = "issuance_type_id")
    private Long issuanceTypeId;

    @Column(name = "pump_id")
    private Long pumpId;

    @Column(name = "storage_unit_id")
    private Long storageUnitId;

    @Column(name = "litres")
    private Float litres;

    @Column(name = "meter_reading_start")
    private Float meterReadingStart;

    @Column(name = "meter_reading_end")
    private Float meterReadingEnd;

    @Column(name = "multiplier")
    private Integer multiplier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FuelTransactionLine id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFuelTransactionLineId() {
        return this.fuelTransactionLineId;
    }

    public FuelTransactionLine fuelTransactionLineId(Long fuelTransactionLineId) {
        this.setFuelTransactionLineId(fuelTransactionLineId);
        return this;
    }

    public void setFuelTransactionLineId(Long fuelTransactionLineId) {
        this.fuelTransactionLineId = fuelTransactionLineId;
    }

    public Long getFuelTransactionHeaderId() {
        return this.fuelTransactionHeaderId;
    }

    public FuelTransactionLine fuelTransactionHeaderId(Long fuelTransactionHeaderId) {
        this.setFuelTransactionHeaderId(fuelTransactionHeaderId);
        return this;
    }

    public void setFuelTransactionHeaderId(Long fuelTransactionHeaderId) {
        this.fuelTransactionHeaderId = fuelTransactionHeaderId;
    }

    public Long getAssetPlantId() {
        return this.assetPlantId;
    }

    public FuelTransactionLine assetPlantId(Long assetPlantId) {
        this.setAssetPlantId(assetPlantId);
        return this;
    }

    public void setAssetPlantId(Long assetPlantId) {
        this.assetPlantId = assetPlantId;
    }

    public Long getContractDivisionId() {
        return this.contractDivisionId;
    }

    public FuelTransactionLine contractDivisionId(Long contractDivisionId) {
        this.setContractDivisionId(contractDivisionId);
        return this;
    }

    public void setContractDivisionId(Long contractDivisionId) {
        this.contractDivisionId = contractDivisionId;
    }

    public Long getIssuanceTypeId() {
        return this.issuanceTypeId;
    }

    public FuelTransactionLine issuanceTypeId(Long issuanceTypeId) {
        this.setIssuanceTypeId(issuanceTypeId);
        return this;
    }

    public void setIssuanceTypeId(Long issuanceTypeId) {
        this.issuanceTypeId = issuanceTypeId;
    }

    public Long getPumpId() {
        return this.pumpId;
    }

    public FuelTransactionLine pumpId(Long pumpId) {
        this.setPumpId(pumpId);
        return this;
    }

    public void setPumpId(Long pumpId) {
        this.pumpId = pumpId;
    }

    public Long getStorageUnitId() {
        return this.storageUnitId;
    }

    public FuelTransactionLine storageUnitId(Long storageUnitId) {
        this.setStorageUnitId(storageUnitId);
        return this;
    }

    public void setStorageUnitId(Long storageUnitId) {
        this.storageUnitId = storageUnitId;
    }

    public Float getLitres() {
        return this.litres;
    }

    public FuelTransactionLine litres(Float litres) {
        this.setLitres(litres);
        return this;
    }

    public void setLitres(Float litres) {
        this.litres = litres;
    }

    public Float getMeterReadingStart() {
        return this.meterReadingStart;
    }

    public FuelTransactionLine meterReadingStart(Float meterReadingStart) {
        this.setMeterReadingStart(meterReadingStart);
        return this;
    }

    public void setMeterReadingStart(Float meterReadingStart) {
        this.meterReadingStart = meterReadingStart;
    }

    public Float getMeterReadingEnd() {
        return this.meterReadingEnd;
    }

    public FuelTransactionLine meterReadingEnd(Float meterReadingEnd) {
        this.setMeterReadingEnd(meterReadingEnd);
        return this;
    }

    public void setMeterReadingEnd(Float meterReadingEnd) {
        this.meterReadingEnd = meterReadingEnd;
    }

    public Integer getMultiplier() {
        return this.multiplier;
    }

    public FuelTransactionLine multiplier(Integer multiplier) {
        this.setMultiplier(multiplier);
        return this;
    }

    public void setMultiplier(Integer multiplier) {
        this.multiplier = multiplier;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FuelTransactionLine)) {
            return false;
        }
        return getId() != null && getId().equals(((FuelTransactionLine) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuelTransactionLine{" +
            "id=" + getId() +
            ", fuelTransactionLineId=" + getFuelTransactionLineId() +
            ", fuelTransactionHeaderId=" + getFuelTransactionHeaderId() +
            ", assetPlantId=" + getAssetPlantId() +
            ", contractDivisionId=" + getContractDivisionId() +
            ", issuanceTypeId=" + getIssuanceTypeId() +
            ", pumpId=" + getPumpId() +
            ", storageUnitId=" + getStorageUnitId() +
            ", litres=" + getLitres() +
            ", meterReadingStart=" + getMeterReadingStart() +
            ", meterReadingEnd=" + getMeterReadingEnd() +
            ", multiplier=" + getMultiplier() +
            "}";
    }
}
