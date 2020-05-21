package es.emilio.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import es.emilio.web.rest.TestUtil;

public class CalendarYearUserDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalendarYearUserDTO.class);
        CalendarYearUserDTO calendarYearUserDTO1 = new CalendarYearUserDTO();
        calendarYearUserDTO1.setId(1L);
        CalendarYearUserDTO calendarYearUserDTO2 = new CalendarYearUserDTO();
        assertThat(calendarYearUserDTO1).isNotEqualTo(calendarYearUserDTO2);
        calendarYearUserDTO2.setId(calendarYearUserDTO1.getId());
        assertThat(calendarYearUserDTO1).isEqualTo(calendarYearUserDTO2);
        calendarYearUserDTO2.setId(2L);
        assertThat(calendarYearUserDTO1).isNotEqualTo(calendarYearUserDTO2);
        calendarYearUserDTO1.setId(null);
        assertThat(calendarYearUserDTO1).isNotEqualTo(calendarYearUserDTO2);
    }
}
