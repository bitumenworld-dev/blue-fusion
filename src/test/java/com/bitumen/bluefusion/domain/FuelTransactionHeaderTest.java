package com.bitumen.bluefusion.domain;

import static com.bitumen.bluefusion.domain.FuelTransactionHeaderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bitumen.bluefusion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FuelTransactionHeaderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuelTransactionHeader.class);
        FuelTransactionHeader fuelTransactionHeader1 = getFuelTransactionHeaderSample1();
        FuelTransactionHeader fuelTransactionHeader2 = new FuelTransactionHeader();
        assertThat(fuelTransactionHeader1).isNotEqualTo(fuelTransactionHeader2);

        fuelTransactionHeader2.setId(fuelTransactionHeader1.getId());
        assertThat(fuelTransactionHeader1).isEqualTo(fuelTransactionHeader2);

        fuelTransactionHeader2 = getFuelTransactionHeaderSample2();
        assertThat(fuelTransactionHeader1).isNotEqualTo(fuelTransactionHeader2);
    }
}
