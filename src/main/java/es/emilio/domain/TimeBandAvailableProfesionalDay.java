package es.emilio.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A TimeBandAvailableProfesionalDay.
 */
@Entity
@Table(name = "time_band_profesional_day")
public class TimeBandAvailableProfesionalDay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private Integer year;

    @Column(name = "start")
    private Instant start;

    @Column(name = "end")
    private Instant end;

    @OneToMany(mappedBy = "timeBandAvailableProfesionalDay")
    private Set<CalendarYearProfesional> calendarYearProfesionals = new HashSet<>();

    @OneToMany(mappedBy = "timeBandAvailableProfesionalDay")
    private Set<Profesional> profesionals = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("timeBandAvailableProfesionalDays")
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public TimeBandAvailableProfesionalDay year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Instant getStart() {
        return start;
    }

    public TimeBandAvailableProfesionalDay start(Instant start) {
        this.start = start;
        return this;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public TimeBandAvailableProfesionalDay end(Instant end) {
        this.end = end;
        return this;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public Set<CalendarYearProfesional> getCalendarYearProfesionals() {
        return calendarYearProfesionals;
    }

    public TimeBandAvailableProfesionalDay calendarYearProfesionals(Set<CalendarYearProfesional> calendarYearProfesionals) {
        this.calendarYearProfesionals = calendarYearProfesionals;
        return this;
    }

    public TimeBandAvailableProfesionalDay addCalendarYearProfesional(CalendarYearProfesional calendarYearProfesional) {
        this.calendarYearProfesionals.add(calendarYearProfesional);
        calendarYearProfesional.setTimeBandAvailableProfesionalDay(this);
        return this;
    }

    public TimeBandAvailableProfesionalDay removeCalendarYearProfesional(CalendarYearProfesional calendarYearProfesional) {
        this.calendarYearProfesionals.remove(calendarYearProfesional);
        calendarYearProfesional.setTimeBandAvailableProfesionalDay(null);
        return this;
    }

    public void setCalendarYearProfesionals(Set<CalendarYearProfesional> calendarYearProfesionals) {
        this.calendarYearProfesionals = calendarYearProfesionals;
    }

    public Set<Profesional> getProfesionals() {
        return profesionals;
    }

    public TimeBandAvailableProfesionalDay profesionals(Set<Profesional> profesionals) {
        this.profesionals = profesionals;
        return this;
    }

    public TimeBandAvailableProfesionalDay addProfesional(Profesional profesional) {
        this.profesionals.add(profesional);
        profesional.setTimeBandAvailableProfesionalDay(this);
        return this;
    }

    public TimeBandAvailableProfesionalDay removeProfesional(Profesional profesional) {
        this.profesionals.remove(profesional);
        profesional.setTimeBandAvailableProfesionalDay(null);
        return this;
    }

    public void setProfesionals(Set<Profesional> profesionals) {
        this.profesionals = profesionals;
    }

    public Company getCompany() {
        return company;
    }

    public TimeBandAvailableProfesionalDay company(Company company) {
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
        if (!(o instanceof TimeBandAvailableProfesionalDay)) {
            return false;
        }
        return id != null && id.equals(((TimeBandAvailableProfesionalDay) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TimeBandAvailableProfesionalDay{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
