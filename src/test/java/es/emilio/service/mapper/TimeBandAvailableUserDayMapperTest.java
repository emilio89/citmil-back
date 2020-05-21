package es.emilio.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeBandAvailableUserDayMapperTest {

    private TimeBandAvailableUserDayMapper timeBandAvailableUserDayMapper;

    @BeforeEach
    public void setUp() {
        timeBandAvailableUserDayMapper = new TimeBandAvailableUserDayMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(timeBandAvailableUserDayMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(timeBandAvailableUserDayMapper.fromId(null)).isNull();
    }
}
