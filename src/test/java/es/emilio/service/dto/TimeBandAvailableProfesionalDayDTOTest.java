package es.emilio.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import es.emilio.web.rest.TestUtil;

public class TimeBandAvailableProfesionalDayDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeBandAvailableProfesionalDayDTO.class);
        TimeBandAvailableProfesionalDayDTO timeBandAvailableProfesionalDayDTO1 = new TimeBandAvailableProfesionalDayDTO();
        timeBandAvailableProfesionalDayDTO1.setId(1L);
        TimeBandAvailableProfesionalDayDTO timeBandAvailableProfesionalDayDTO2 = new TimeBandAvailableProfesionalDayDTO();
        assertThat(timeBandAvailableProfesionalDayDTO1).isNotEqualTo(timeBandAvailableProfesionalDayDTO2);
        timeBandAvailableProfesionalDayDTO2.setId(timeBandAvailableProfesionalDayDTO1.getId());
        assertThat(timeBandAvailableProfesionalDayDTO1).isEqualTo(timeBandAvailableProfesionalDayDTO2);
        timeBandAvailableProfesionalDayDTO2.setId(2L);
        assertThat(timeBandAvailableProfesionalDayDTO1).isNotEqualTo(timeBandAvailableProfesionalDayDTO2);
        timeBandAvailableProfesionalDayDTO1.setId(null);
        assertThat(timeBandAvailableProfesionalDayDTO1).isNotEqualTo(timeBandAvailableProfesionalDayDTO2);
    }
}
