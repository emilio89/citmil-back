package es.emilio.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * A DTO for the {@link es.emilio.domain.TimeBand} entity.
 */
@Data
public class GenerateCalendarMonthDTO implements Serializable {
    
    private List<UserDTO> users;
    private Instant month;
    
    private Map<Integer,List<TimeBandStringDayDTO>> days;

   
}
