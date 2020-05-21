package es.emilio.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import es.emilio.web.rest.TestUtil;

public class TimeBandTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeBand.class);
        TimeBand timeBand1 = new TimeBand();
        timeBand1.setId(1L);
        TimeBand timeBand2 = new TimeBand();
        timeBand2.setId(timeBand1.getId());
        assertThat(timeBand1).isEqualTo(timeBand2);
        timeBand2.setId(2L);
        assertThat(timeBand1).isNotEqualTo(timeBand2);
        timeBand1.setId(null);
        assertThat(timeBand1).isNotEqualTo(timeBand2);
    }
}
