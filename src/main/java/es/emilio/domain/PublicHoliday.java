package es.emilio.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A PublicHoliday.
 */
@Entity
@Table(name = "public_holiday")
public class PublicHoliday implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day")
    private LocalDate day;

    @Column(name = "year")
    private Integer year;

    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @Size(max = 12)
    @Column(name = "dni", length = 12)
    private String dni;

    @ManyToOne
    @JsonIgnoreProperties("publicHolidays")
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return day;
    }

    public PublicHoliday day(LocalDate day) {
        this.day = day;
        return this;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Integer getYear() {
        return year;
    }

    public PublicHoliday year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public PublicHoliday name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDni() {
        return dni;
    }

    public PublicHoliday dni(String dni) {
        this.dni = dni;
        return this;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Company getCompany() {
        return company;
    }

    public PublicHoliday company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublicHoliday)) {
            return false;
        }
        return id != null && id.equals(((PublicHoliday) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PublicHoliday{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", year=" + getYear() +
            ", name='" + getName() + "'" +
            ", dni='" + getDni() + "'" +
            "}";
    }
}
