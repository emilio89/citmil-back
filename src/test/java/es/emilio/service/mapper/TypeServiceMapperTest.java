package es.emilio.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeServiceMapperTest {

    private TypeServiceMapper typeServiceMapper;

    @BeforeEach
    public void setUp() {
        typeServiceMapper = new TypeServiceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(typeServiceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(typeServiceMapper.fromId(null)).isNull();
    }
}
