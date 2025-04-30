package com.bitumen.bluefusion.domain;

import com.bitumen.bluefusion.domain.enumeration.ConsumptionUnit;
import com.bitumen.bluefusion.domain.enumeration.DriverOrOperator;
import com.bitumen.bluefusion.domain.enumeration.FuelType;
import com.bitumen.bluefusion.domain.enumeration.HorseOrTrailer;
import com.bitumen.bluefusion.domain.enumeration.PlantHoursStatus;
import com.bitumen.bluefusion.domain.enumeration.SMRReaderType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AssetPlant.
 */
@Entity
@Table(name = "asset_plant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssetPlant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "asset_plant_id")
    private Long assetPlantId;

    @Size(max = 30)
    @Column(name = "fleet_number", length = 30)
    private String fleetNumber;

    @Size(max = 30)
    @Column(name = "number_plate", length = 30)
    private String numberPlate;

    @Size(max = 200)
    @Column(name = "fleet_description", length = 200)
    private String fleetDescription;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "accessible_by_company")
    private String accessibleByCompany;

    @Enumerated(EnumType.STRING)
    @Column(name = "driver_or_operator")
    private DriverOrOperator driverOrOperator;

    @Column(name = "plant_category_id")
    private Long plantCategoryId;

    @Column(name = "plant_subcategory_id")
    private String plantSubcategoryId;

    @Column(name = "manufacturer_id")
    private Long manufacturerId;

    @Column(name = "model_id")
    private Long modelId;

    @Column(name = "year_of_manufacture")
    private Integer yearOfManufacture;

    @Size(max = 20)
    @Column(name = "colour", length = 20)
    private String colour;

    @Enumerated(EnumType.STRING)
    @Column(name = "horse_or_trailer")
    private HorseOrTrailer horseOrTrailer;

    @Enumerated(EnumType.STRING)
    @Column(name = "smr_reader_type")
    private SMRReaderType smrReaderType;

    @Column(name = "current_smr_index")
    private Integer currentSmrIndex;

    @Size(max = 50)
    @Column(name = "engine_number", length = 50)
    private String engineNumber;

    @Size(max = 30)
    @Column(name = "engine_capacity_cc", length = 30)
    private String engineCapacityCc;

    @Column(name = "current_site_id")
    private Long currentSiteId;

    @Column(name = "current_contract_id")
    private Long currentContractId;

    @Column(name = "current_operator_id")
    private Long currentOperatorId;

    @Size(max = 30)
    @Column(name = "ledger_code", length = 30)
    private String ledgerCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private FuelType fuelType;

    @Column(name = "tank_capacity_litres")
    private Float tankCapacityLitres;

    @Enumerated(EnumType.STRING)
    @Column(name = "consumption_unit")
    private ConsumptionUnit consumptionUnit;

    @Enumerated(EnumType.STRING)
    @Column(name = "plant_hours_status")
    private PlantHoursStatus plantHoursStatus;

    @Column(name = "is_prime_mover")
    private Boolean isPrimeMover;

    @Column(name = "capacity_tons")
    private Float capacityTons;

    @Column(name = "capacity_m_3_loose")
    private Float capacityM3Loose;

    @Column(name = "capacity_m_3_tight")
    private Float capacityM3Tight;

    @Column(name = "maximum_consumption")
    private Float maximumConsumption;

    @Column(name = "minimum_consumption")
    private Float minimumConsumption;

    @Column(name = "maximum_smr_on_full_tank")
    private Float maximumSmrOnFullTank;

    @Column(name = "track_consumption")
    private Boolean trackConsumption;

    @Column(name = "track_smr_reading")
    private Boolean trackSmrReading;

    @Column(name = "track_service")
    private Boolean trackService;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "request_weekly_mileage")
    private Boolean requestWeeklyMileage;

    @Column(name = "sent")
    private Boolean sent;

    @Size(max = 30)
    @Column(name = "chassis_number", length = 30)
    private String chassisNumber;

    @Size(max = 30)
    @Column(name = "current_location", length = 30)
    private String currentLocation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AssetPlant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssetPlantId() {
        return this.assetPlantId;
    }

    public AssetPlant assetPlantId(Long assetPlantId) {
        this.setAssetPlantId(assetPlantId);
        return this;
    }

    public void setAssetPlantId(Long assetPlantId) {
        this.assetPlantId = assetPlantId;
    }

    public String getFleetNumber() {
        return this.fleetNumber;
    }

    public AssetPlant fleetNumber(String fleetNumber) {
        this.setFleetNumber(fleetNumber);
        return this;
    }

    public void setFleetNumber(String fleetNumber) {
        this.fleetNumber = fleetNumber;
    }

    public String getNumberPlate() {
        return this.numberPlate;
    }

    public AssetPlant numberPlate(String numberPlate) {
        this.setNumberPlate(numberPlate);
        return this;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getFleetDescription() {
        return this.fleetDescription;
    }

    public AssetPlant fleetDescription(String fleetDescription) {
        this.setFleetDescription(fleetDescription);
        return this;
    }

    public void setFleetDescription(String fleetDescription) {
        this.fleetDescription = fleetDescription;
    }

    public Long getOwnerId() {
        return this.ownerId;
    }

    public AssetPlant ownerId(Long ownerId) {
        this.setOwnerId(ownerId);
        return this;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getAccessibleByCompany() {
        return this.accessibleByCompany;
    }

    public AssetPlant accessibleByCompany(String accessibleByCompany) {
        this.setAccessibleByCompany(accessibleByCompany);
        return this;
    }

    public void setAccessibleByCompany(String accessibleByCompany) {
        this.accessibleByCompany = accessibleByCompany;
    }

    public DriverOrOperator getDriverOrOperator() {
        return this.driverOrOperator;
    }

    public AssetPlant driverOrOperator(DriverOrOperator driverOrOperator) {
        this.setDriverOrOperator(driverOrOperator);
        return this;
    }

    public void setDriverOrOperator(DriverOrOperator driverOrOperator) {
        this.driverOrOperator = driverOrOperator;
    }

    public Long getPlantCategoryId() {
        return this.plantCategoryId;
    }

    public AssetPlant plantCategoryId(Long plantCategoryId) {
        this.setPlantCategoryId(plantCategoryId);
        return this;
    }

    public void setPlantCategoryId(Long plantCategoryId) {
        this.plantCategoryId = plantCategoryId;
    }

    public String getPlantSubcategoryId() {
        return this.plantSubcategoryId;
    }

    public AssetPlant plantSubcategoryId(String plantSubcategoryId) {
        this.setPlantSubcategoryId(plantSubcategoryId);
        return this;
    }

    public void setPlantSubcategoryId(String plantSubcategoryId) {
        this.plantSubcategoryId = plantSubcategoryId;
    }

    public Long getManufacturerId() {
        return this.manufacturerId;
    }

    public AssetPlant manufacturerId(Long manufacturerId) {
        this.setManufacturerId(manufacturerId);
        return this;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Long getModelId() {
        return this.modelId;
    }

    public AssetPlant modelId(Long modelId) {
        this.setModelId(modelId);
        return this;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Integer getYearOfManufacture() {
        return this.yearOfManufacture;
    }

    public AssetPlant yearOfManufacture(Integer yearOfManufacture) {
        this.setYearOfManufacture(yearOfManufacture);
        return this;
    }

    public void setYearOfManufacture(Integer yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public String getColour() {
        return this.colour;
    }

    public AssetPlant colour(String colour) {
        this.setColour(colour);
        return this;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public HorseOrTrailer getHorseOrTrailer() {
        return this.horseOrTrailer;
    }

    public AssetPlant horseOrTrailer(HorseOrTrailer horseOrTrailer) {
        this.setHorseOrTrailer(horseOrTrailer);
        return this;
    }

    public void setHorseOrTrailer(HorseOrTrailer horseOrTrailer) {
        this.horseOrTrailer = horseOrTrailer;
    }

    public SMRReaderType getSmrReaderType() {
        return this.smrReaderType;
    }

    public AssetPlant smrReaderType(SMRReaderType smrReaderType) {
        this.setSmrReaderType(smrReaderType);
        return this;
    }

    public void setSmrReaderType(SMRReaderType smrReaderType) {
        this.smrReaderType = smrReaderType;
    }

    public Integer getCurrentSmrIndex() {
        return this.currentSmrIndex;
    }

    public AssetPlant currentSmrIndex(Integer currentSmrIndex) {
        this.setCurrentSmrIndex(currentSmrIndex);
        return this;
    }

    public void setCurrentSmrIndex(Integer currentSmrIndex) {
        this.currentSmrIndex = currentSmrIndex;
    }

    public String getEngineNumber() {
        return this.engineNumber;
    }

    public AssetPlant engineNumber(String engineNumber) {
        this.setEngineNumber(engineNumber);
        return this;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getEngineCapacityCc() {
        return this.engineCapacityCc;
    }

    public AssetPlant engineCapacityCc(String engineCapacityCc) {
        this.setEngineCapacityCc(engineCapacityCc);
        return this;
    }

    public void setEngineCapacityCc(String engineCapacityCc) {
        this.engineCapacityCc = engineCapacityCc;
    }

    public Long getCurrentSiteId() {
        return this.currentSiteId;
    }

    public AssetPlant currentSiteId(Long currentSiteId) {
        this.setCurrentSiteId(currentSiteId);
        return this;
    }

    public void setCurrentSiteId(Long currentSiteId) {
        this.currentSiteId = currentSiteId;
    }

    public Long getCurrentContractId() {
        return this.currentContractId;
    }

    public AssetPlant currentContractId(Long currentContractId) {
        this.setCurrentContractId(currentContractId);
        return this;
    }

    public void setCurrentContractId(Long currentContractId) {
        this.currentContractId = currentContractId;
    }

    public Long getCurrentOperatorId() {
        return this.currentOperatorId;
    }

    public AssetPlant currentOperatorId(Long currentOperatorId) {
        this.setCurrentOperatorId(currentOperatorId);
        return this;
    }

    public void setCurrentOperatorId(Long currentOperatorId) {
        this.currentOperatorId = currentOperatorId;
    }

    public String getLedgerCode() {
        return this.ledgerCode;
    }

    public AssetPlant ledgerCode(String ledgerCode) {
        this.setLedgerCode(ledgerCode);
        return this;
    }

    public void setLedgerCode(String ledgerCode) {
        this.ledgerCode = ledgerCode;
    }

    public FuelType getFuelType() {
        return this.fuelType;
    }

    public AssetPlant fuelType(FuelType fuelType) {
        this.setFuelType(fuelType);
        return this;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Float getTankCapacityLitres() {
        return this.tankCapacityLitres;
    }

    public AssetPlant tankCapacityLitres(Float tankCapacityLitres) {
        this.setTankCapacityLitres(tankCapacityLitres);
        return this;
    }

    public void setTankCapacityLitres(Float tankCapacityLitres) {
        this.tankCapacityLitres = tankCapacityLitres;
    }

    public ConsumptionUnit getConsumptionUnit() {
        return this.consumptionUnit;
    }

    public AssetPlant consumptionUnit(ConsumptionUnit consumptionUnit) {
        this.setConsumptionUnit(consumptionUnit);
        return this;
    }

    public void setConsumptionUnit(ConsumptionUnit consumptionUnit) {
        this.consumptionUnit = consumptionUnit;
    }

    public PlantHoursStatus getPlantHoursStatus() {
        return this.plantHoursStatus;
    }

    public AssetPlant plantHoursStatus(PlantHoursStatus plantHoursStatus) {
        this.setPlantHoursStatus(plantHoursStatus);
        return this;
    }

    public void setPlantHoursStatus(PlantHoursStatus plantHoursStatus) {
        this.plantHoursStatus = plantHoursStatus;
    }

    public Boolean getIsPrimeMover() {
        return this.isPrimeMover;
    }

    public AssetPlant isPrimeMover(Boolean isPrimeMover) {
        this.setIsPrimeMover(isPrimeMover);
        return this;
    }

    public void setIsPrimeMover(Boolean isPrimeMover) {
        this.isPrimeMover = isPrimeMover;
    }

    public Float getCapacityTons() {
        return this.capacityTons;
    }

    public AssetPlant capacityTons(Float capacityTons) {
        this.setCapacityTons(capacityTons);
        return this;
    }

    public void setCapacityTons(Float capacityTons) {
        this.capacityTons = capacityTons;
    }

    public Float getCapacityM3Loose() {
        return this.capacityM3Loose;
    }

    public AssetPlant capacityM3Loose(Float capacityM3Loose) {
        this.setCapacityM3Loose(capacityM3Loose);
        return this;
    }

    public void setCapacityM3Loose(Float capacityM3Loose) {
        this.capacityM3Loose = capacityM3Loose;
    }

    public Float getCapacityM3Tight() {
        return this.capacityM3Tight;
    }

    public AssetPlant capacityM3Tight(Float capacityM3Tight) {
        this.setCapacityM3Tight(capacityM3Tight);
        return this;
    }

    public void setCapacityM3Tight(Float capacityM3Tight) {
        this.capacityM3Tight = capacityM3Tight;
    }

    public Float getMaximumConsumption() {
        return this.maximumConsumption;
    }

    public AssetPlant maximumConsumption(Float maximumConsumption) {
        this.setMaximumConsumption(maximumConsumption);
        return this;
    }

    public void setMaximumConsumption(Float maximumConsumption) {
        this.maximumConsumption = maximumConsumption;
    }

    public Float getMinimumConsumption() {
        return this.minimumConsumption;
    }

    public AssetPlant minimumConsumption(Float minimumConsumption) {
        this.setMinimumConsumption(minimumConsumption);
        return this;
    }

    public void setMinimumConsumption(Float minimumConsumption) {
        this.minimumConsumption = minimumConsumption;
    }

    public Float getMaximumSmrOnFullTank() {
        return this.maximumSmrOnFullTank;
    }

    public AssetPlant maximumSmrOnFullTank(Float maximumSmrOnFullTank) {
        this.setMaximumSmrOnFullTank(maximumSmrOnFullTank);
        return this;
    }

    public void setMaximumSmrOnFullTank(Float maximumSmrOnFullTank) {
        this.maximumSmrOnFullTank = maximumSmrOnFullTank;
    }

    public Boolean getTrackConsumption() {
        return this.trackConsumption;
    }

    public AssetPlant trackConsumption(Boolean trackConsumption) {
        this.setTrackConsumption(trackConsumption);
        return this;
    }

    public void setTrackConsumption(Boolean trackConsumption) {
        this.trackConsumption = trackConsumption;
    }

    public Boolean getTrackSmrReading() {
        return this.trackSmrReading;
    }

    public AssetPlant trackSmrReading(Boolean trackSmrReading) {
        this.setTrackSmrReading(trackSmrReading);
        return this;
    }

    public void setTrackSmrReading(Boolean trackSmrReading) {
        this.trackSmrReading = trackSmrReading;
    }

    public Boolean getTrackService() {
        return this.trackService;
    }

    public AssetPlant trackService(Boolean trackService) {
        this.setTrackService(trackService);
        return this;
    }

    public void setTrackService(Boolean trackService) {
        this.trackService = trackService;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public AssetPlant isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getRequestWeeklyMileage() {
        return this.requestWeeklyMileage;
    }

    public AssetPlant requestWeeklyMileage(Boolean requestWeeklyMileage) {
        this.setRequestWeeklyMileage(requestWeeklyMileage);
        return this;
    }

    public void setRequestWeeklyMileage(Boolean requestWeeklyMileage) {
        this.requestWeeklyMileage = requestWeeklyMileage;
    }

    public Boolean getSent() {
        return this.sent;
    }

    public AssetPlant sent(Boolean sent) {
        this.setSent(sent);
        return this;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public String getChassisNumber() {
        return this.chassisNumber;
    }

    public AssetPlant chassisNumber(String chassisNumber) {
        this.setChassisNumber(chassisNumber);
        return this;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getCurrentLocation() {
        return this.currentLocation;
    }

    public AssetPlant currentLocation(String currentLocation) {
        this.setCurrentLocation(currentLocation);
        return this;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssetPlant)) {
            return false;
        }
        return getId() != null && getId().equals(((AssetPlant) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetPlant{" +
            "id=" + getId() +
            ", assetPlantId=" + getAssetPlantId() +
            ", fleetNumber='" + getFleetNumber() + "'" +
            ", numberPlate='" + getNumberPlate() + "'" +
            ", fleetDescription='" + getFleetDescription() + "'" +
            ", ownerId=" + getOwnerId() +
            ", accessibleByCompany='" + getAccessibleByCompany() + "'" +
            ", driverOrOperator='" + getDriverOrOperator() + "'" +
            ", plantCategoryId=" + getPlantCategoryId() +
            ", plantSubcategoryId='" + getPlantSubcategoryId() + "'" +
            ", manufacturerId=" + getManufacturerId() +
            ", modelId=" + getModelId() +
            ", yearOfManufacture=" + getYearOfManufacture() +
            ", colour='" + getColour() + "'" +
            ", horseOrTrailer='" + getHorseOrTrailer() + "'" +
            ", smrReaderType='" + getSmrReaderType() + "'" +
            ", currentSmrIndex=" + getCurrentSmrIndex() +
            ", engineNumber='" + getEngineNumber() + "'" +
            ", engineCapacityCc='" + getEngineCapacityCc() + "'" +
            ", currentSiteId=" + getCurrentSiteId() +
            ", currentContractId=" + getCurrentContractId() +
            ", currentOperatorId=" + getCurrentOperatorId() +
            ", ledgerCode='" + getLedgerCode() + "'" +
            ", fuelType='" + getFuelType() + "'" +
            ", tankCapacityLitres=" + getTankCapacityLitres() +
            ", consumptionUnit='" + getConsumptionUnit() + "'" +
            ", plantHoursStatus='" + getPlantHoursStatus() + "'" +
            ", isPrimeMover='" + getIsPrimeMover() + "'" +
            ", capacityTons=" + getCapacityTons() +
            ", capacityM3Loose=" + getCapacityM3Loose() +
            ", capacityM3Tight=" + getCapacityM3Tight() +
            ", maximumConsumption=" + getMaximumConsumption() +
            ", minimumConsumption=" + getMinimumConsumption() +
            ", maximumSmrOnFullTank=" + getMaximumSmrOnFullTank() +
            ", trackConsumption='" + getTrackConsumption() + "'" +
            ", trackSmrReading='" + getTrackSmrReading() + "'" +
            ", trackService='" + getTrackService() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", requestWeeklyMileage='" + getRequestWeeklyMileage() + "'" +
            ", sent='" + getSent() + "'" +
            ", chassisNumber='" + getChassisNumber() + "'" +
            ", currentLocation='" + getCurrentLocation() + "'" +
            "}";
    }
}
