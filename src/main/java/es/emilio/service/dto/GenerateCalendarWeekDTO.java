package es.emilio.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import lombok.Data;

/**
 * A DTO for the {@link es.emilio.domain.TimeBand} entity.
 */
@Data
public class GenerateCalendarWeekDTO implements Serializable {
    
    private List<UserDTO> users;

    private List<TimeBandDayDTO> timeBandsDay;

   
}
