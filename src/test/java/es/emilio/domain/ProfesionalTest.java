package es.emilio.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import es.emilio.web.rest.TestUtil;

public class ProfesionalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profesional.class);
        Profesional profesional1 = new Profesional();
        profesional1.setId(1L);
        Profesional profesional2 = new Profesional();
        profesional2.setId(profesional1.getId());
        assertThat(profesional1).isEqualTo(profesional2);
        profesional2.setId(2L);
        assertThat(profesional1).isNotEqualTo(profesional2);
        profesional1.setId(null);
        assertThat(profesional1).isNotEqualTo(profesional2);
    }
}
