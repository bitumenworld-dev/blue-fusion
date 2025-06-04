package com.bitumen.bluefusion.domain;

import com.bitumen.bluefusion.domain.enumeration.ServiceUnit;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AssetPlantServiceReading.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "asset_plant_service_reading")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssetPlantServiceReading extends AbstractAuditingEntity<AssetPlantServiceReading> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
