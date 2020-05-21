package es.emilio.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A TimeBandAvailableUserDay.
 */
@Entity
@Table(name = "time_band_available_user_day")
public class TimeBandAvailableUserDay implements Serializable {

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

    @OneToMany(mappedBy = "timeBandAvailableUserDay")
    private Set<CalendarYearUser> calendarYearUsers = new HashSet<>();

    @OneToMany(mappedBy = "timeBandAvailableUserDay")
    private Set<UserExtra> userExtras = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("timeBandAvailableUserDays")
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

    public TimeBandAvailableUserDay year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Instant getStart() {
        return start;
    }

    public TimeBandAvailableUserDay start(Instant start) {
        this.start = start;
        return this;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public TimeBandAvailableUserDay end(Instant end) {
        this.end = end;
        return this;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public Set<CalendarYearUser> getCalendarYearUsers() {
        return calendarYearUsers;
    }

    public TimeBandAvailableUserDay calendarYearUsers(Set<CalendarYearUser> calendarYearUsers) {
        this.calendarYearUsers = calendarYearUsers;
        return this;
    }

    public TimeBandAvailableUserDay addCalendarYearUser(CalendarYearUser calendarYearUser) {
        this.calendarYearUsers.add(calendarYearUser);
        calendarYearUser.setTimeBandAvailableUserDay(this);
        return this;
    }

    public TimeBandAvailableUserDay removeCalendarYearUser(CalendarYearUser calendarYearUser) {
        this.calendarYearUsers.remove(calendarYearUser);
        calendarYearUser.setTimeBandAvailableUserDay(null);
        return this;
    }

    public void setCalendarYearUsers(Set<CalendarYearUser> calendarYearUsers) {
        this.calendarYearUsers = calendarYearUsers;
    }

    public Set<UserExtra> getUserExtras() {
        return userExtras;
    }

    public TimeBandAvailableUserDay userExtras(Set<UserExtra> userExtras) {
        this.userExtras = userExtras;
        return this;
    }

    public TimeBandAvailableUserDay addUserExtra(UserExtra userExtra) {
        this.userExtras.add(userExtra);
        userExtra.setTimeBandAvailableUserDay(this);
        return this;
    }

    public TimeBandAvailableUserDay removeUserExtra(UserExtra userExtra) {
        this.userExtras.remove(userExtra);
        userExtra.setTimeBandAvailableUserDay(null);
        return this;
    }

    public void setUserExtras(Set<UserExtra> userExtras) {
        this.userExtras = userExtras;
    }

    public Company getCompany() {
        return company;
    }

    public TimeBandAvailableUserDay company(Company company) {
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
        if (!(o instanceof TimeBandAvailableUserDay)) {
            return false;
        }
        return id != null && id.equals(((TimeBandAvailableUserDay) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TimeBandAvailableUserDay{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
