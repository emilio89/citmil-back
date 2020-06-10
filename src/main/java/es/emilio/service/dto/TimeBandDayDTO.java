package es.emilio.service.dto;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

/**
 * A DTO for the {@link es.emilio.domain.TimeBand} entity.
 */
@Data
public class TimeBandDayDTO implements Serializable {
    
    private Instant day;

    private String start;

    private String end;


}
