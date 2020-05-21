package es.emilio.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CalendarYearUserMapperTest {

    private CalendarYearUserMapper calendarYearUserMapper;

    @BeforeEach
    public void setUp() {
        calendarYearUserMapper = new CalendarYearUserMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(calendarYearUserMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(calendarYearUserMapper.fromId(null)).isNull();
    }
}
