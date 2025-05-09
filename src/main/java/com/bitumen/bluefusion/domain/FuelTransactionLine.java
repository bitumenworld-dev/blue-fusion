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
    private AssetPlant assetPlantId;

    @ManyToOne
    @JoinColumn(name = "contract_division_id")
    private ContractDivision contractDivisionId;

    @ManyToOne
    @JoinColumn(name = "issuance_type_id")
    private FuelIssuanceType issuanceTypeId;

    @ManyToOne
    @JoinColumn(name = "pump_id")
    private FuelPump pumpId;

    @Column(name = "litres")
    private Float litres;

    @Column(name = "meter_reading_start")
    private Float meterReadingStart;

    @Column(name = "meter_reading_end")
    private Float meterReadingEnd;

    @Column(name = "multiplier")
    private Integer multiplier;
}
