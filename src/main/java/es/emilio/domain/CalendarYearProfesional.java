package es.emilio.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A CalendarYearProfesional.
 */
@Entity
@Table(name = "calendar_year_profesional")
public class CalendarYearProfesional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day")
    private LocalDate day;

    @Column(name = "year")
    private Integer year;

    @Column(name = "is_public_holiday")
    private Boolean isPublicHoliday;

    @Column(name = "start")
    private Instant start;

    @Column(name = "end")
    private Instant end;

    @OneToMany(mappedBy = "calendarYearProfesional")
    private Set<Profesional> profesionals = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("calendarYearProfesionals")
    private Company company;

    @ManyToOne
    @JsonIgnoreProperties("calendarYearProfesionals")
    private TimeBandAvailableProfesionalDay timeBandAvailableProfesionalDay;

    @ManyToMany(mappedBy = "calendarYearProfesionals")
    @JsonIgnore
    private Set<TimeBand> timeBands = new HashSet<>();

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

    public CalendarYearProfesional day(LocalDate day) {
        this.day = day;
        return this;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Integer getYear() {
        return year;
    }

    public CalendarYearProfesional year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean isIsPublicHoliday() {
        return isPublicHoliday;
    }

    public CalendarYearProfesional isPublicHoliday(Boolean isPublicHoliday) {
        this.isPublicHoliday = isPublicHoliday;
        return this;
    }

    public void setIsPublicHoliday(Boolean isPublicHoliday) {
        this.isPublicHoliday = isPublicHoliday;
    }

    public Instant getStart() {
        return start;
    }

    public CalendarYearProfesional start(Instant start) {
        this.start = start;
        return this;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public CalendarYearProfesional end(Instant end) {
        this.end = end;
        return this;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public Set<Profesional> getProfesionals() {
        return profesionals;
    }

    public CalendarYearProfesional profesionals(Set<Profesional> profesionals) {
        this.profesionals = profesionals;
        return this;
    }

    public CalendarYearProfesional addProfesional(Profesional profesional) {
        this.profesionals.add(profesional);
        profesional.setCalendarYearProfesional(this);
        return this;
    }

    public CalendarYearProfesional removeProfesional(Profesional profesional) {
        this.profesionals.remove(profesional);
        profesional.setCalendarYearProfesional(null);
        return this;
    }

    public void setProfesionals(Set<Profesional> profesionals) {
        this.profesionals = profesionals;
    }

    public Company getCompany() {
        return company;
    }

    public CalendarYearProfesional company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public TimeBandAvailableProfesionalDay getTimeBandAvailableProfesionalDay() {
        return timeBandAvailableProfesionalDay;
    }

    public CalendarYearProfesional timeBandAvailableProfesionalDay(TimeBandAvailableProfesionalDay timeBandAvailableProfesionalDay) {
        this.timeBandAvailableProfesionalDay = timeBandAvailableProfesionalDay;
        return this;
    }

    public void setTimeBandAvailableProfesionalDay(TimeBandAvailableProfesionalDay timeBandAvailableProfesionalDay) {
        this.timeBandAvailableProfesionalDay = timeBandAvailableProfesionalDay;
    }

    public Set<TimeBand> getTimeBands() {
        return timeBands;
    }

    public CalendarYearProfesional timeBands(Set<TimeBand> timeBands) {
        this.timeBands = timeBands;
        return this;
    }

    public CalendarYearProfesional addTimeBand(TimeBand timeBand) {
        this.timeBands.add(timeBand);
        timeBand.getCalendarYearProfesionals().add(this);
        return this;
    }

    public CalendarYearProfesional removeTimeBand(TimeBand timeBand) {
        this.timeBands.remove(timeBand);
        timeBand.getCalendarYearProfesionals().remove(this);
        return this;
    }

    public void setTimeBands(Set<TimeBand> timeBands) {
        this.timeBands = timeBands;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CalendarYearProfesional)) {
            return false;
        }
        return id != null && id.equals(((CalendarYearProfesional) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CalendarYearProfesional{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", year=" + getYear() +
            ", isPublicHoliday='" + isIsPublicHoliday() + "'" +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
