package es.emilio.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MenuOptionsAvailableMapperTest {

    private MenuOptionsAvailableMapper menuOptionsAvailableMapper;

    @BeforeEach
    public void setUp() {
        menuOptionsAvailableMapper = new MenuOptionsAvailableMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(menuOptionsAvailableMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(menuOptionsAvailableMapper.fromId(null)).isNull();
    }
}
