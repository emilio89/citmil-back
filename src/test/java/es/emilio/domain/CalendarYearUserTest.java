package es.emilio.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import es.emilio.web.rest.TestUtil;

public class CalendarYearUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalendarYearUser.class);
        CalendarYearUser calendarYearUser1 = new CalendarYearUser();
        calendarYearUser1.setId(1L);
        CalendarYearUser calendarYearUser2 = new CalendarYearUser();
        calendarYearUser2.setId(calendarYearUser1.getId());
        assertThat(calendarYearUser1).isEqualTo(calendarYearUser2);
        calendarYearUser2.setId(2L);
        assertThat(calendarYearUser1).isNotEqualTo(calendarYearUser2);
        calendarYearUser1.setId(null);
        assertThat(calendarYearUser1).isNotEqualTo(calendarYearUser2);
    }
}
