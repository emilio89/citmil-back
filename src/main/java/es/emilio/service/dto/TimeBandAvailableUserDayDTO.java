package es.emilio.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link es.emilio.domain.TimeBandAvailableUserDay} entity.
 */
public class TimeBandAvailableUserDayDTO implements Serializable {
    
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

        TimeBandAvailableUserDayDTO timeBandAvailableUserDayDTO = (TimeBandAvailableUserDayDTO) o;
        if (timeBandAvailableUserDayDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timeBandAvailableUserDayDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TimeBandAvailableUserDayDTO{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
