package es.emilio.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import es.emilio.web.rest.TestUtil;

public class MenuOptionsAvailableDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenuOptionsAvailableDTO.class);
        MenuOptionsAvailableDTO menuOptionsAvailableDTO1 = new MenuOptionsAvailableDTO();
        menuOptionsAvailableDTO1.setId(1L);
        MenuOptionsAvailableDTO menuOptionsAvailableDTO2 = new MenuOptionsAvailableDTO();
        assertThat(menuOptionsAvailableDTO1).isNotEqualTo(menuOptionsAvailableDTO2);
        menuOptionsAvailableDTO2.setId(menuOptionsAvailableDTO1.getId());
        assertThat(menuOptionsAvailableDTO1).isEqualTo(menuOptionsAvailableDTO2);
        menuOptionsAvailableDTO2.setId(2L);
        assertThat(menuOptionsAvailableDTO1).isNotEqualTo(menuOptionsAvailableDTO2);
        menuOptionsAvailableDTO1.setId(null);
        assertThat(menuOptionsAvailableDTO1).isNotEqualTo(menuOptionsAvailableDTO2);
    }
}
