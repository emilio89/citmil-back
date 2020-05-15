package es.emilio.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import es.emilio.web.rest.TestUtil;

public class TimeBandAvailableProfesionalDayTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeBandAvailableProfesionalDay.class);
        TimeBandAvailableProfesionalDay timeBandAvailableProfesionalDay1 = new TimeBandAvailableProfesionalDay();
        timeBandAvailableProfesionalDay1.setId(1L);
        TimeBandAvailableProfesionalDay timeBandAvailableProfesionalDay2 = new TimeBandAvailableProfesionalDay();
        timeBandAvailableProfesionalDay2.setId(timeBandAvailableProfesionalDay1.getId());
        assertThat(timeBandAvailableProfesionalDay1).isEqualTo(timeBandAvailableProfesionalDay2);
        timeBandAvailableProfesionalDay2.setId(2L);
        assertThat(timeBandAvailableProfesionalDay1).isNotEqualTo(timeBandAvailableProfesionalDay2);
        timeBandAvailableProfesionalDay1.setId(null);
        assertThat(timeBandAvailableProfesionalDay1).isNotEqualTo(timeBandAvailableProfesionalDay2);
    }
}
