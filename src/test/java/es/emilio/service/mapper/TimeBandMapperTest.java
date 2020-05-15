package es.emilio.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeBandMapperTest {

    private TimeBandMapper timeBandMapper;

    @BeforeEach
    public void setUp() {
        timeBandMapper = new TimeBandMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(timeBandMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(timeBandMapper.fromId(null)).isNull();
    }
}
