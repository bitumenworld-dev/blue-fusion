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
import jdk.jfr.DataAmount;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AssetPlant.
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "asset_plant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AssetPlant extends AbstractAuditingEntity<AssetPlant> implements Serializable {

    private static final long serialVersionUID = 1L;

    //
    //    @Id
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //    @Column(name = "id")
    //    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Long getAssetPlantId() {
        return assetPlantId;
    }

    public void setAssetPlantId(Long assetPlantId) {
        this.assetPlantId = assetPlantId;
    }

    public String getFleetNumber() {
        return fleetNumber;
    }

    public void setFleetNumber(String fleetNumber) {
        this.fleetNumber = fleetNumber;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getFleetDescription() {
        return fleetDescription;
    }

    public void setFleetDescription(String fleetDescription) {
        this.fleetDescription = fleetDescription;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getAccessibleByCompany() {
        return accessibleByCompany;
    }

    public void setAccessibleByCompany(String accessibleByCompany) {
        this.accessibleByCompany = accessibleByCompany;
    }

    public DriverOrOperator getDriverOrOperator() {
        return driverOrOperator;
    }

    public void setDriverOrOperator(DriverOrOperator driverOrOperator) {
        this.driverOrOperator = driverOrOperator;
    }

    public Long getPlantCategoryId() {
        return plantCategoryId;
    }

    public void setPlantCategoryId(Long plantCategoryId) {
        this.plantCategoryId = plantCategoryId;
    }

    public String getPlantSubcategoryId() {
        return plantSubcategoryId;
    }

    public void setPlantSubcategoryId(String plantSubcategoryId) {
        this.plantSubcategoryId = plantSubcategoryId;
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Integer getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(Integer yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public HorseOrTrailer getHorseOrTrailer() {
        return horseOrTrailer;
    }

    public void setHorseOrTrailer(HorseOrTrailer horseOrTrailer) {
        this.horseOrTrailer = horseOrTrailer;
    }

    public SMRReaderType getSmrReaderType() {
        return smrReaderType;
    }

    public void setSmrReaderType(SMRReaderType smrReaderType) {
        this.smrReaderType = smrReaderType;
    }

    public Integer getCurrentSmrIndex() {
        return currentSmrIndex;
    }

    public void setCurrentSmrIndex(Integer currentSmrIndex) {
        this.currentSmrIndex = currentSmrIndex;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getEngineCapacityCc() {
        return engineCapacityCc;
    }

    public void setEngineCapacityCc(String engineCapacityCc) {
        this.engineCapacityCc = engineCapacityCc;
    }

    public Long getCurrentSiteId() {
        return currentSiteId;
    }

    public void setCurrentSiteId(Long currentSiteId) {
        this.currentSiteId = currentSiteId;
    }

    public Long getCurrentContractId() {
        return currentContractId;
    }

    public void setCurrentContractId(Long currentContractId) {
        this.currentContractId = currentContractId;
    }

    public Long getCurrentOperatorId() {
        return currentOperatorId;
    }

    public void setCurrentOperatorId(Long currentOperatorId) {
        this.currentOperatorId = currentOperatorId;
    }

    public String getLedgerCode() {
        return ledgerCode;
    }

    public void setLedgerCode(String ledgerCode) {
        this.ledgerCode = ledgerCode;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Float getTankCapacityLitres() {
        return tankCapacityLitres;
    }

    public void setTankCapacityLitres(Float tankCapacityLitres) {
        this.tankCapacityLitres = tankCapacityLitres;
    }

    public ConsumptionUnit getConsumptionUnit() {
        return consumptionUnit;
    }

    public void setConsumptionUnit(ConsumptionUnit consumptionUnit) {
        this.consumptionUnit = consumptionUnit;
    }

    public PlantHoursStatus getPlantHoursStatus() {
        return plantHoursStatus;
    }

    public void setPlantHoursStatus(PlantHoursStatus plantHoursStatus) {
        this.plantHoursStatus = plantHoursStatus;
    }

    public Boolean getPrimeMover() {
        return isPrimeMover;
    }

    public void setPrimeMover(Boolean primeMover) {
        isPrimeMover = primeMover;
    }

    public Float getCapacityTons() {
        return capacityTons;
    }

    public void setCapacityTons(Float capacityTons) {
        this.capacityTons = capacityTons;
    }

    public Float getCapacityM3Loose() {
        return capacityM3Loose;
    }

    public void setCapacityM3Loose(Float capacityM3Loose) {
        this.capacityM3Loose = capacityM3Loose;
    }

    public Float getCapacityM3Tight() {
        return capacityM3Tight;
    }

    public void setCapacityM3Tight(Float capacityM3Tight) {
        this.capacityM3Tight = capacityM3Tight;
    }

    public Float getMaximumConsumption() {
        return maximumConsumption;
    }

    public void setMaximumConsumption(Float maximumConsumption) {
        this.maximumConsumption = maximumConsumption;
    }

    public Float getMinimumConsumption() {
        return minimumConsumption;
    }

    public void setMinimumConsumption(Float minimumConsumption) {
        this.minimumConsumption = minimumConsumption;
    }

    public Float getMaximumSmrOnFullTank() {
        return maximumSmrOnFullTank;
    }

    public void setMaximumSmrOnFullTank(Float maximumSmrOnFullTank) {
        this.maximumSmrOnFullTank = maximumSmrOnFullTank;
    }

    public Boolean getTrackConsumption() {
        return trackConsumption;
    }

    public void setTrackConsumption(Boolean trackConsumption) {
        this.trackConsumption = trackConsumption;
    }

    public Boolean getTrackSmrReading() {
        return trackSmrReading;
    }

    public void setTrackSmrReading(Boolean trackSmrReading) {
        this.trackSmrReading = trackSmrReading;
    }

    public Boolean getTrackService() {
        return trackService;
    }

    public void setTrackService(Boolean trackService) {
        this.trackService = trackService;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getRequestWeeklyMileage() {
        return requestWeeklyMileage;
    }

    public void setRequestWeeklyMileage(Boolean requestWeeklyMileage) {
        this.requestWeeklyMileage = requestWeeklyMileage;
    }

    public Boolean getSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
}
