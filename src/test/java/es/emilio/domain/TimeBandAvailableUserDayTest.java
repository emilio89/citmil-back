package es.emilio.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import es.emilio.web.rest.TestUtil;

public class TimeBandAvailableUserDayTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeBandAvailableUserDay.class);
        TimeBandAvailableUserDay timeBandAvailableUserDay1 = new TimeBandAvailableUserDay();
        timeBandAvailableUserDay1.setId(1L);
        TimeBandAvailableUserDay timeBandAvailableUserDay2 = new TimeBandAvailableUserDay();
        timeBandAvailableUserDay2.setId(timeBandAvailableUserDay1.getId());
        assertThat(timeBandAvailableUserDay1).isEqualTo(timeBandAvailableUserDay2);
        timeBandAvailableUserDay2.setId(2L);
        assertThat(timeBandAvailableUserDay1).isNotEqualTo(timeBandAvailableUserDay2);
        timeBandAvailableUserDay1.setId(null);
        assertThat(timeBandAvailableUserDay1).isNotEqualTo(timeBandAvailableUserDay2);
    }
}
