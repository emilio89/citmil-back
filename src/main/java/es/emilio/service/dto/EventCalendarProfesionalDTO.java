package es.emilio.service.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventCalendarProfesionalDTO {
    private Long idUser;
    private String nameProfesional;
    private Instant start;

    private Instant end;

}
