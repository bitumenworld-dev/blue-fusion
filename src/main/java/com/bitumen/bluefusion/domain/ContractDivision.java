package com.bitumen.bluefusion.domain;

import com.bitumen.bluefusion.domain.enumeration.ContractDivisionType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContractDivision.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contract_division")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContractDivision extends AbstractAuditingEntity<ContractDivision> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_division_id")
    private Long contractDivisionId;

    @Column(name = "contract_division_number")
    private String contractDivisionNumber;

    @JoinColumn(name = "company_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @Column(name = "contract_division_name")
    private String contractDivisionName;

    @Column(name = "build_smart_reference")
    private String buildSmartReference;

    @Column(name = "shift_start")
    private LocalTime shiftStart;

    @Column(name = "shift_end")
    private LocalTime shiftEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ContractDivisionType contractDivisionType;

    @Column(name = "completed")
    private Boolean completed;

    @Column(name = "hr_hours_monday_thursday")
    private Double hrHoursMondayThursday;

    @Column(name = "hr_hours_friday")
    private Double hrHoursFriday;

    @Column(name = "add_hours_monday_friday")
    private Double addHoursMondayFriday;

    @Column(name = "add_hours_weekend")
    private Double addHoursWeekend;
}
