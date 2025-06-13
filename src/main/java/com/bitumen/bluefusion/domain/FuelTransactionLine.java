package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;

/**
 * A FuelTransactionLine.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fuel_transaction_line")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuelTransactionLine extends AbstractAuditingEntity<FuelTransactionLine> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fuel_transaction_line_id")
    private Long fuelTransactionLineId;

    @ManyToOne
    @JoinColumn(name = "fuel_transaction_header_id")
    private FuelTransactionHeader fuelTransactionHeader;

    @ManyToOne
    @JoinColumn(name = "asset_plant_id")
    private AssetPlant assetPlant;

    @ManyToOne
    @JoinColumn(name = "workshop_id")
    private Site workshop;

    @ManyToOne
    @JoinColumn(name = "third_party_id")
    private ThirdParty thirdParty;

    @ManyToOne
    @JoinColumn(name = "transfer_unit_id")
    private Storage transferUnit;

    @ManyToOne
    @JoinColumn(name = "contract_division_id")
    private ContractDivision contractDivision;

    @ManyToOne
    @JoinColumn(name = "pump_id")
    private FuelPump pump;

    @Column(name = "litres")
    private Float litres;

    @Column(name = "meter_reading_start")
    private Float meterReadingStart;

    @Column(name = "meter_reading_end")
    private Float meterReadingEnd;

    @Column(name = "multiplier")
    private Integer multiplier;

    @Column(name = "registration_number")
    private String registrationNumber;
}
