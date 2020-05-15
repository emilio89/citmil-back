package es.emilio.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DynamicContentMapperTest {

    private DynamicContentMapper dynamicContentMapper;

    @BeforeEach
    public void setUp() {
        dynamicContentMapper = new DynamicContentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(dynamicContentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(dynamicContentMapper.fromId(null)).isNull();
    }
}
