package es.emilio.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A TimeBand.
 */
@Entity
@Table(name = "time_band")
public class TimeBand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start")
    private Instant start;

    @Column(name = "end")
    private Instant end;

    @ManyToMany
    @JoinTable(name = "time_band_calendar_year_profesional",
               joinColumns = @JoinColumn(name = "time_band_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "calendar_year_profesional_id", referencedColumnName = "id"))
    private Set<CalendarYearProfesional> calendarYearProfesionals = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("timeBands")
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStart() {
        return start;
    }

    public TimeBand start(Instant start) {
        this.start = start;
        return this;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public TimeBand end(Instant end) {
        this.end = end;
        return this;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public Set<CalendarYearProfesional> getCalendarYearProfesionals() {
        return calendarYearProfesionals;
    }

    public TimeBand calendarYearProfesionals(Set<CalendarYearProfesional> calendarYearProfesionals) {
        this.calendarYearProfesionals = calendarYearProfesionals;
        return this;
    }

    public TimeBand addCalendarYearProfesional(CalendarYearProfesional calendarYearProfesional) {
        this.calendarYearProfesionals.add(calendarYearProfesional);
        calendarYearProfesional.getTimeBands().add(this);
        return this;
    }

    public TimeBand removeCalendarYearProfesional(CalendarYearProfesional calendarYearProfesional) {
        this.calendarYearProfesionals.remove(calendarYearProfesional);
        calendarYearProfesional.getTimeBands().remove(this);
        return this;
    }

    public void setCalendarYearProfesionals(Set<CalendarYearProfesional> calendarYearProfesionals) {
        this.calendarYearProfesionals = calendarYearProfesionals;
    }

    public Company getCompany() {
        return company;
    }

    public TimeBand company(Company company) {
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
        if (!(o instanceof TimeBand)) {
            return false;
        }
        return id != null && id.equals(((TimeBand) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TimeBand{" +
            "id=" + getId() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
