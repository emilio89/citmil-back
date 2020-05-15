package es.emilio.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import es.emilio.web.rest.TestUtil;

public class TypeServiceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeServiceDTO.class);
        TypeServiceDTO typeServiceDTO1 = new TypeServiceDTO();
        typeServiceDTO1.setId(1L);
        TypeServiceDTO typeServiceDTO2 = new TypeServiceDTO();
        assertThat(typeServiceDTO1).isNotEqualTo(typeServiceDTO2);
        typeServiceDTO2.setId(typeServiceDTO1.getId());
        assertThat(typeServiceDTO1).isEqualTo(typeServiceDTO2);
        typeServiceDTO2.setId(2L);
        assertThat(typeServiceDTO1).isNotEqualTo(typeServiceDTO2);
        typeServiceDTO1.setId(null);
        assertThat(typeServiceDTO1).isNotEqualTo(typeServiceDTO2);
    }
}
