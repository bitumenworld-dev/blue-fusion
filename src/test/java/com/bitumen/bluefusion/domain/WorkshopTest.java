package com.bitumen.bluefusion.domain;

import static com.bitumen.bluefusion.domain.WorkshopTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bitumen.bluefusion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkshopTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Workshop.class);
        Workshop workshop1 = getWorkshopSample1();
        Workshop workshop2 = new Workshop();
        assertThat(workshop1).isNotEqualTo(workshop2);

        workshop2.setId(workshop1.getId());
        assertThat(workshop1).isEqualTo(workshop2);

        workshop2 = getWorkshopSample2();
        assertThat(workshop1).isNotEqualTo(workshop2);
    }
}
