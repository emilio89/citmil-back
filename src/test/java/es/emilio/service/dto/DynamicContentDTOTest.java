package es.emilio.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import es.emilio.web.rest.TestUtil;

public class DynamicContentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DynamicContentDTO.class);
        DynamicContentDTO dynamicContentDTO1 = new DynamicContentDTO();
        dynamicContentDTO1.setId(1L);
        DynamicContentDTO dynamicContentDTO2 = new DynamicContentDTO();
        assertThat(dynamicContentDTO1).isNotEqualTo(dynamicContentDTO2);
        dynamicContentDTO2.setId(dynamicContentDTO1.getId());
        assertThat(dynamicContentDTO1).isEqualTo(dynamicContentDTO2);
        dynamicContentDTO2.setId(2L);
        assertThat(dynamicContentDTO1).isNotEqualTo(dynamicContentDTO2);
        dynamicContentDTO1.setId(null);
        assertThat(dynamicContentDTO1).isNotEqualTo(dynamicContentDTO2);
    }
}
