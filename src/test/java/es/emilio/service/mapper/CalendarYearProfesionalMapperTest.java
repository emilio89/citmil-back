package es.emilio.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CalendarYearProfesionalMapperTest {

    private CalendarYearProfesionalMapper calendarYearProfesionalMapper;

    @BeforeEach
    public void setUp() {
        calendarYearProfesionalMapper = new CalendarYearProfesionalMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(calendarYearProfesionalMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(calendarYearProfesionalMapper.fromId(null)).isNull();
    }
}
