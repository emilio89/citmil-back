package es.emilio.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

import lombok.Data;

/**
 * A DTO for a day.
 */
@Data
public class DayDTO implements Serializable {
    
    private Integer number;

    private String day;


}
