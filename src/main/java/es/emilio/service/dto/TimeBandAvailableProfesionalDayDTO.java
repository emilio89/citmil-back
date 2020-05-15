package es.emilio.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link es.emilio.domain.TimeBandAvailableProfesionalDay} entity.
 */
public class TimeBandAvailableProfesionalDayDTO implements Serializable {
    
    private Long id;

    private Integer year;

    private Instant start;

    private Instant end;


    private Long companyId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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

        TimeBandAvailableProfesionalDayDTO timeBandAvailableProfesionalDayDTO = (TimeBandAvailableProfesionalDayDTO) o;
        if (timeBandAvailableProfesionalDayDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timeBandAvailableProfesionalDayDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TimeBandAvailableProfesionalDayDTO{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
