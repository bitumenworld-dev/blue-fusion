package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plant_subcategory")
public class PlantSubcategory extends AbstractAuditingEntity<PlantSubcategory> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_subcategory_id")
    private Long plantSubcategoryId;

    @Column(name = "plant_subcategory_code")
    private String plantSubcategoryCode;

    @Column(name = "plant_subcategory_description")
    private String plantSubcategoryDescription;
}
