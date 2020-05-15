package es.emilio.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import es.emilio.web.rest.TestUtil;

public class DynamicContentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DynamicContent.class);
        DynamicContent dynamicContent1 = new DynamicContent();
        dynamicContent1.setId(1L);
        DynamicContent dynamicContent2 = new DynamicContent();
        dynamicContent2.setId(dynamicContent1.getId());
        assertThat(dynamicContent1).isEqualTo(dynamicContent2);
        dynamicContent2.setId(2L);
        assertThat(dynamicContent1).isNotEqualTo(dynamicContent2);
        dynamicContent1.setId(null);
        assertThat(dynamicContent1).isNotEqualTo(dynamicContent2);
    }
}
