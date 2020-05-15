package es.emilio.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import es.emilio.web.rest.TestUtil;

public class CalendarYearProfesionalDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalendarYearProfesionalDTO.class);
        CalendarYearProfesionalDTO calendarYearProfesionalDTO1 = new CalendarYearProfesionalDTO();
        calendarYearProfesionalDTO1.setId(1L);
        CalendarYearProfesionalDTO calendarYearProfesionalDTO2 = new CalendarYearProfesionalDTO();
        assertThat(calendarYearProfesionalDTO1).isNotEqualTo(calendarYearProfesionalDTO2);
        calendarYearProfesionalDTO2.setId(calendarYearProfesionalDTO1.getId());
        assertThat(calendarYearProfesionalDTO1).isEqualTo(calendarYearProfesionalDTO2);
        calendarYearProfesionalDTO2.setId(2L);
        assertThat(calendarYearProfesionalDTO1).isNotEqualTo(calendarYearProfesionalDTO2);
        calendarYearProfesionalDTO1.setId(null);
        assertThat(calendarYearProfesionalDTO1).isNotEqualTo(calendarYearProfesionalDTO2);
    }
}
