package es.emilio.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import es.emilio.web.rest.TestUtil;

public class MenuOptionsAvailableTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenuOptionsAvailable.class);
        MenuOptionsAvailable menuOptionsAvailable1 = new MenuOptionsAvailable();
        menuOptionsAvailable1.setId(1L);
        MenuOptionsAvailable menuOptionsAvailable2 = new MenuOptionsAvailable();
        menuOptionsAvailable2.setId(menuOptionsAvailable1.getId());
        assertThat(menuOptionsAvailable1).isEqualTo(menuOptionsAvailable2);
        menuOptionsAvailable2.setId(2L);
        assertThat(menuOptionsAvailable1).isNotEqualTo(menuOptionsAvailable2);
        menuOptionsAvailable1.setId(null);
        assertThat(menuOptionsAvailable1).isNotEqualTo(menuOptionsAvailable2);
    }
}
