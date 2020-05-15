package es.emilio.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import es.emilio.web.rest.TestUtil;

public class CalendarYearProfesionalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalendarYearProfesional.class);
        CalendarYearProfesional calendarYearProfesional1 = new CalendarYearProfesional();
        calendarYearProfesional1.setId(1L);
        CalendarYearProfesional calendarYearProfesional2 = new CalendarYearProfesional();
        calendarYearProfesional2.setId(calendarYearProfesional1.getId());
        assertThat(calendarYearProfesional1).isEqualTo(calendarYearProfesional2);
        calendarYearProfesional2.setId(2L);
        assertThat(calendarYearProfesional1).isNotEqualTo(calendarYearProfesional2);
        calendarYearProfesional1.setId(null);
        assertThat(calendarYearProfesional1).isNotEqualTo(calendarYearProfesional2);
    }
}
