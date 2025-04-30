package com.bitumen.bluefusion.domain;

import static com.bitumen.bluefusion.domain.FuelTransactionLineTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bitumen.bluefusion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FuelTransactionLineTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuelTransactionLine.class);
        FuelTransactionLine fuelTransactionLine1 = getFuelTransactionLineSample1();
        FuelTransactionLine fuelTransactionLine2 = new FuelTransactionLine();
        assertThat(fuelTransactionLine1).isNotEqualTo(fuelTransactionLine2);

        fuelTransactionLine2.setId(fuelTransactionLine1.getId());
        assertThat(fuelTransactionLine1).isEqualTo(fuelTransactionLine2);

        fuelTransactionLine2 = getFuelTransactionLineSample2();
        assertThat(fuelTransactionLine1).isNotEqualTo(fuelTransactionLine2);
    }
}
