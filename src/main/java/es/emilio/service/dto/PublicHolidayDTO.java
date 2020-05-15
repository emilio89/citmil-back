package es.emilio.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link es.emilio.domain.PublicHoliday} entity.
 */
public class PublicHolidayDTO implements Serializable {
    
    private Long id;

    private LocalDate day;

    private Integer year;

    @Size(max = 50)
    private String name;

    @Size(max = 12)
    private String dni;


    private Long companyId;
    
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

        PublicHolidayDTO publicHolidayDTO = (PublicHolidayDTO) o;
        if (publicHolidayDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), publicHolidayDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PublicHolidayDTO{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", year=" + getYear() +
            ", name='" + getName() + "'" +
            ", dni='" + getDni() + "'" +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
