package com.bitumen.bluefusion.domain;

import static com.bitumen.bluefusion.domain.FuelTransactionTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bitumen.bluefusion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FuelTransactionTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuelTransactionType.class);
        FuelTransactionType fuelTransactionType1 = getFuelTransactionTypeSample1();
        FuelTransactionType fuelTransactionType2 = new FuelTransactionType();
        assertThat(fuelTransactionType1).isNotEqualTo(fuelTransactionType2);

        fuelTransactionType2.setId(fuelTransactionType1.getId());
        assertThat(fuelTransactionType1).isEqualTo(fuelTransactionType2);

        fuelTransactionType2 = getFuelTransactionTypeSample2();
        assertThat(fuelTransactionType1).isNotEqualTo(fuelTransactionType2);
    }
}
