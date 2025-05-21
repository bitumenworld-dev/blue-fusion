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
@Table(name = "plant_subcategory")
public class PlantSubcategory extends AbstractAuditingEntity<FuelPump> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_subcategory_id")
    private Long plantSubcategoryId;

    @Column(name = "plant_subcategory_code")
    private String plantSubcategoryCode;

    @Column(name = "plant_subcategory_description")
    private String plantSubcategoryDescription;
}
