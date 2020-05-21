package es.emilio.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link es.emilio.domain.CalendarYearUser} entity.
 */
public class CalendarYearUserDTO implements Serializable {
    
    private Long id;

    private LocalDate day;

    private Integer year;

    private Boolean isPublicHoliday;

    private Instant start;

    private Instant end;


    private Long companyId;

    private Long timeBandAvailableUserDayId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean isIsPublicHoliday() {
        return isPublicHoliday;
    }

    public void setIsPublicHoliday(Boolean isPublicHoliday) {
        this.isPublicHoliday = isPublicHoliday;
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

    public Long getTimeBandAvailableUserDayId() {
        return timeBandAvailableUserDayId;
    }

    public void setTimeBandAvailableUserDayId(Long timeBandAvailableUserDayId) {
        this.timeBandAvailableUserDayId = timeBandAvailableUserDayId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CalendarYearUserDTO calendarYearUserDTO = (CalendarYearUserDTO) o;
        if (calendarYearUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), calendarYearUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CalendarYearUserDTO{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", year=" + getYear() +
            ", isPublicHoliday='" + isIsPublicHoliday() + "'" +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", companyId=" + getCompanyId() +
            ", timeBandAvailableUserDayId=" + getTimeBandAvailableUserDayId() +
            "}";
    }
}
