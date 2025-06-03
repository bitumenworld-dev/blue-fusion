package com.bitumen.bluefusion.domain;

import com.bitumen.bluefusion.domain.enumeration.DriverOrOperator;
import com.bitumen.bluefusion.domain.enumeration.FuelType;
import com.bitumen.bluefusion.domain.enumeration.HorseOrTrailer;
import com.bitumen.bluefusion.domain.enumeration.SMRReaderType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "asset_plant")
public class AssetPlant extends AbstractAuditingEntity<AssetPlant> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Company owner;

    @Size(max = 30)
    @Column(name = "chassis_number", length = 30)
    private String chassisNumber;

    @ManyToMany
    @JoinTable(
        name = "asset_plant_company_access",
        joinColumns = @JoinColumn(name = "asset_plant_id"),
        inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private List<Company> accessibleByCompany;

    @Column(name = "year_of_manufacture")
    private Integer yearOfManufacture;

    @Size(max = 20)
    @Column(name = "colour", length = 20)
    private String colour;

    @Column(name = "current_smr_index")
    private Integer currentSmrIndex;

    @Size(max = 50)
    @Column(name = "engine_number", length = 50)
    private String engineNumber;

    @Size(max = 30)
    @Column(name = "engine_capacity_cc", length = 30)
    private String engineCapacityCC;

    @ManyToOne
    @JoinColumn(name = "current_site_id")
    private Site currentSite;

    @ManyToOne
    @JoinColumn(name = "current_contract_id")
    private ContractDivision currentContract;

    @Size(max = 30)
    @Column(name = "ledger_code", length = 30)
    private String ledgerCode;

    @Column(name = "tank_capacity_litres")
    private Float tankCapacityLitres;

    @Column(name = "capacity_tons")
    private Float capacityTons;

    @Column(name = "capacity_m3_loose")
    private Float capacityM3Loose;

    @Column(name = "capacity_m3_tight")
    private Float capacityM3Tight;

    @Column(name = "maximum_consumption")
    private Float maximumConsumption;

    @Column(name = "minimum_consumption")
    private Float minimumConsumption;

    @Column(name = "maximum_smr_on_full_tank")
    private Float maximumSmrOnFullTank;

    @Column(name = "track_consumption")
    private Boolean trackConsumption;

    @Column(name = "track_service")
    private Boolean trackService;

    @Column(name = "track_smr_reading")
    private Boolean trackSmrReading;

    @Column(name = "request_weekly_mileage")
    private Boolean requestWeeklyMileage;

    @Column(name = "sent")
    private Boolean sent;

    @Size(max = 30)
    @Column(name = "current_location", length = 30)
    private String currentLocation;

    @ManyToOne
    @JoinColumn(name = "plant_category_id")
    private PlantCategory plantCategory;

    @ManyToOne
    @JoinColumn(name = "plant_subcategory_id")
    private PlantSubcategory plantSubcategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "driver_or_operator")
    private DriverOrOperator driverOrOperator;

    @ManyToOne
    @JoinColumn(name = "make_id")
    private Make make;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private MakeModel model;

    @Enumerated(EnumType.STRING)
    @Column(name = "horse_or_trailer")
    private HorseOrTrailer horseOrTrailer;

    @Enumerated(EnumType.STRING)
    @Column(name = "smr_reader_type")
    private SMRReaderType smrReaderType;

    @ManyToOne
    @JoinColumn(name = "current_operator_id")
    private Employee currentOperator;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private FuelType fuelType;

    @Column(name = "is_prime_mover")
    private Boolean isPrimeMover;

    @Column(name = "is_active")
    private Boolean isActive;
}
