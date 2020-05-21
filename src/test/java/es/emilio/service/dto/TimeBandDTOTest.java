package es.emilio.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import es.emilio.web.rest.TestUtil;

public class TimeBandDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeBandDTO.class);
        TimeBandDTO timeBandDTO1 = new TimeBandDTO();
        timeBandDTO1.setId(1L);
        TimeBandDTO timeBandDTO2 = new TimeBandDTO();
        assertThat(timeBandDTO1).isNotEqualTo(timeBandDTO2);
        timeBandDTO2.setId(timeBandDTO1.getId());
        assertThat(timeBandDTO1).isEqualTo(timeBandDTO2);
        timeBandDTO2.setId(2L);
        assertThat(timeBandDTO1).isNotEqualTo(timeBandDTO2);
        timeBandDTO1.setId(null);
        assertThat(timeBandDTO1).isNotEqualTo(timeBandDTO2);
    }
}
