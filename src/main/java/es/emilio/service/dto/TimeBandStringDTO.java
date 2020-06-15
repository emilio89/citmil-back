package es.emilio.service.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * A DTO for the {@link es.emilio.domain.TimeBandStringDTO} entity.
 */
@Data
public class TimeBandStringDTO implements Serializable {
    

    private String start;

    private String end;


}
