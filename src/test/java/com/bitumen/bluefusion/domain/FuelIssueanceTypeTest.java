package com.bitumen.bluefusion.domain;

import static com.bitumen.bluefusion.domain.FuelIssueanceTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bitumen.bluefusion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FuelIssueanceTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuelIssueanceType.class);
        FuelIssueanceType fuelIssueanceType1 = getFuelIssueanceTypeSample1();
        FuelIssueanceType fuelIssueanceType2 = new FuelIssueanceType();
        assertThat(fuelIssueanceType1).isNotEqualTo(fuelIssueanceType2);

        fuelIssueanceType2.setId(fuelIssueanceType1.getId());
        assertThat(fuelIssueanceType1).isEqualTo(fuelIssueanceType2);

        fuelIssueanceType2 = getFuelIssueanceTypeSample2();
        assertThat(fuelIssueanceType1).isNotEqualTo(fuelIssueanceType2);
    }
}
