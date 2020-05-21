package es.emilio.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link es.emilio.domain.TimeBand} entity.
 */
public class TimeBandDTO implements Serializable {
    
    private Long id;

    private Instant start;

    private Instant end;

    private Set<CalendarYearUserDTO> calendarYearUsers = new HashSet<>();

    private Long companyId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public Set<CalendarYearUserDTO> getCalendarYearUsers() {
        return calendarYearUsers;
    }

    public void setCalendarYearUsers(Set<CalendarYearUserDTO> calendarYearUsers) {
        this.calendarYearUsers = calendarYearUsers;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TimeBandDTO timeBandDTO = (TimeBandDTO) o;
        if (timeBandDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timeBandDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TimeBandDTO{" +
            "id=" + getId() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", calendarYearUsers='" + getCalendarYearUsers() + "'" +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
