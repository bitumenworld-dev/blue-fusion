package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appraisals_table")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Appraisals extends AbstractAuditingEntity<Appraisals> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appraisal_id")
    private Long appraisalId;

    @Column(name = "employee_number")
    private String employeeNumber;

    @Column(name = "appraisal_period")
    private Integer appraisalPeriod;

    @Column(name = "appraisal_value")
    private Integer appraisalValue;
}
