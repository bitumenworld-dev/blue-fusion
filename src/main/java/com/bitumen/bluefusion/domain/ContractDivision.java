package com.bitumen.bluefusion.domain;

import com.bitumen.bluefusion.domain.enumeration.ContractDivisionType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContractDivision.
 */
@Data
@Entity
@Table(name = "contract_division")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContractDivision implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_division_id")
    private Long contractDivisionId;

    @Column(name = "contract_division_number")
    private String contractDivisionNumber;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "build_smart_reference")
    private String buildSmartReference;

    @Column(name = "shift_start")
    private LocalTime shiftStart;

    @Column(name = "shift_end")
    private LocalTime shiftEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ContractDivisionType type;

    @Column(name = "completed")
    private Boolean completed;

    //TODO: adds to bd diagram and liquibase
    //    monday_to_thursday_working_hours float
    //    friday_working_hours float
    //    add_hours_monday_to_friday float
    //    add_hours_weekend float
    //    contractDivisionName

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.contractDivisionId;
    }

    public void setId(Long id) {
        this.contractDivisionId = id;
    }
}
