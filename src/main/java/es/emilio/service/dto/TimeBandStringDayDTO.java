package es.emilio.service.dto;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

/**
 * A DTO for the {@link es.emilio.domain.TimeBand} entity.
 */
@Data
public class TimeBandStringDayDTO implements Serializable {
    
    private DayDTO day;

    private TimeBandStringDTO timeBand;


}
