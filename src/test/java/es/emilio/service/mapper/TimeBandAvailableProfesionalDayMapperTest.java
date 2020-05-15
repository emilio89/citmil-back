package es.emilio.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeBandAvailableProfesionalDayMapperTest {

    private TimeBandAvailableProfesionalDayMapper timeBandAvailableProfesionalDayMapper;

    @BeforeEach
    public void setUp() {
        timeBandAvailableProfesionalDayMapper = new TimeBandAvailableProfesionalDayMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(timeBandAvailableProfesionalDayMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(timeBandAvailableProfesionalDayMapper.fromId(null)).isNull();
    }
}
