package es.emilio.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import es.emilio.web.rest.TestUtil;

public class TimeBandAvailableUserDayDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeBandAvailableUserDayDTO.class);
        TimeBandAvailableUserDayDTO timeBandAvailableUserDayDTO1 = new TimeBandAvailableUserDayDTO();
        timeBandAvailableUserDayDTO1.setId(1L);
        TimeBandAvailableUserDayDTO timeBandAvailableUserDayDTO2 = new TimeBandAvailableUserDayDTO();
        assertThat(timeBandAvailableUserDayDTO1).isNotEqualTo(timeBandAvailableUserDayDTO2);
        timeBandAvailableUserDayDTO2.setId(timeBandAvailableUserDayDTO1.getId());
        assertThat(timeBandAvailableUserDayDTO1).isEqualTo(timeBandAvailableUserDayDTO2);
        timeBandAvailableUserDayDTO2.setId(2L);
        assertThat(timeBandAvailableUserDayDTO1).isNotEqualTo(timeBandAvailableUserDayDTO2);
        timeBandAvailableUserDayDTO1.setId(null);
        assertThat(timeBandAvailableUserDayDTO1).isNotEqualTo(timeBandAvailableUserDayDTO2);
    }
}
