package es.emilio.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProfesionalMapperTest {

    private ProfesionalMapper profesionalMapper;

    @BeforeEach
    public void setUp() {
        profesionalMapper = new ProfesionalMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(profesionalMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(profesionalMapper.fromId(null)).isNull();
    }
}
