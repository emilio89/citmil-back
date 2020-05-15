package es.emilio.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import es.emilio.web.rest.TestUtil;

public class ProfesionalDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfesionalDTO.class);
        ProfesionalDTO profesionalDTO1 = new ProfesionalDTO();
        profesionalDTO1.setId(1L);
        ProfesionalDTO profesionalDTO2 = new ProfesionalDTO();
        assertThat(profesionalDTO1).isNotEqualTo(profesionalDTO2);
        profesionalDTO2.setId(profesionalDTO1.getId());
        assertThat(profesionalDTO1).isEqualTo(profesionalDTO2);
        profesionalDTO2.setId(2L);
        assertThat(profesionalDTO1).isNotEqualTo(profesionalDTO2);
        profesionalDTO1.setId(null);
        assertThat(profesionalDTO1).isNotEqualTo(profesionalDTO2);
    }
}
