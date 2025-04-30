package com.bitumen.bluefusion.domain;

import static com.bitumen.bluefusion.domain.PlantSubcategoryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bitumen.bluefusion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlantSubcategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlantSubcategory.class);
        PlantSubcategory plantSubcategory1 = getPlantSubcategorySample1();
        PlantSubcategory plantSubcategory2 = new PlantSubcategory();
        assertThat(plantSubcategory1).isNotEqualTo(plantSubcategory2);

        plantSubcategory2.setId(plantSubcategory1.getId());
        assertThat(plantSubcategory1).isEqualTo(plantSubcategory2);

        plantSubcategory2 = getPlantSubcategorySample2();
        assertThat(plantSubcategory1).isNotEqualTo(plantSubcategory2);
    }
}
