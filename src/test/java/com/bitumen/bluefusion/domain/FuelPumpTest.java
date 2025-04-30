package com.bitumen.bluefusion.domain;

import static com.bitumen.bluefusion.domain.FuelPumpTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bitumen.bluefusion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FuelPumpTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuelPump.class);
        FuelPump fuelPump1 = getFuelPumpSample1();
        FuelPump fuelPump2 = new FuelPump();
        assertThat(fuelPump1).isNotEqualTo(fuelPump2);

        fuelPump2.setId(fuelPump1.getId());
        assertThat(fuelPump1).isEqualTo(fuelPump2);

        fuelPump2 = getFuelPumpSample2();
        assertThat(fuelPump1).isNotEqualTo(fuelPump2);
    }
}
