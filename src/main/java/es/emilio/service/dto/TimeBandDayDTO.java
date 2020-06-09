package es.emilio.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import lombok.Data;

/**
 * A DTO for the {@link es.emilio.domain.TimeBand} entity.
 */
@Data
public class TimeBandDayDTO implements Serializable {
    
    private Instant day;

    private Instant start;

    private Instant end;


}
