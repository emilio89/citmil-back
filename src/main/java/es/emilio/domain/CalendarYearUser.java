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
 * A CalendarYearUser.
 */
@Entity
@Table(name = "calendar_year_user")
public class CalendarYearUser implements Serializable {

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

    @OneToMany(mappedBy = "calendarYearUser")
    private Set<UserExtra> userExtras = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("calendarYearUsers")
    private Company company;

    @ManyToOne
    @JsonIgnoreProperties("calendarYearUsers")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("calendarYearUsers")
    private TimeBandAvailableUserDay timeBandAvailableUserDay;

    @ManyToMany(mappedBy = "calendarYearUsers", cascade = CascadeType.ALL)
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

    public CalendarYearUser day(LocalDate day) {
        this.day = day;
        return this;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Integer getYear() {
        return year;
    }

    public CalendarYearUser year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean isIsPublicHoliday() {
        return isPublicHoliday;
    }

    public CalendarYearUser isPublicHoliday(Boolean isPublicHoliday) {
        this.isPublicHoliday = isPublicHoliday;
        return this;
    }

    public void setIsPublicHoliday(Boolean isPublicHoliday) {
        this.isPublicHoliday = isPublicHoliday;
    }

    public Instant getStart() {
        return start;
    }

    public CalendarYearUser start(Instant start) {
        this.start = start;
        return this;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public CalendarYearUser end(Instant end) {
        this.end = end;
        return this;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public Set<UserExtra> getUserExtras() {
        return userExtras;
    }

    public CalendarYearUser userExtras(Set<UserExtra> userExtras) {
        this.userExtras = userExtras;
        return this;
    }

    public CalendarYearUser addUserExtra(UserExtra userExtra) {
        this.userExtras.add(userExtra);
        userExtra.setCalendarYearUser(this);
        return this;
    }

    public CalendarYearUser removeUserExtra(UserExtra userExtra) {
        this.userExtras.remove(userExtra);
        userExtra.setCalendarYearUser(null);
        return this;
    }

    public void setUserExtras(Set<UserExtra> userExtras) {
        this.userExtras = userExtras;
    }

    public Company getCompany() {
        return company;
    }

    public CalendarYearUser company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TimeBandAvailableUserDay getTimeBandAvailableUserDay() {
        return timeBandAvailableUserDay;
    }

    public CalendarYearUser timeBandAvailableUserDay(TimeBandAvailableUserDay timeBandAvailableUserDay) {
        this.timeBandAvailableUserDay = timeBandAvailableUserDay;
        return this;
    }

    public void setTimeBandAvailableUserDay(TimeBandAvailableUserDay timeBandAvailableUserDay) {
        this.timeBandAvailableUserDay = timeBandAvailableUserDay;
    }

    public Set<TimeBand> getTimeBands() {
        return timeBands;
    }

    public CalendarYearUser timeBands(Set<TimeBand> timeBands) {
        this.timeBands = timeBands;
        return this;
    }

    public CalendarYearUser addTimeBand(TimeBand timeBand) {
        this.timeBands.add(timeBand);
        timeBand.getCalendarYearUsers().add(this);
        return this;
    }

    public CalendarYearUser removeTimeBand(TimeBand timeBand) {
        this.timeBands.remove(timeBand);
        timeBand.getCalendarYearUsers().remove(this);
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
        if (!(o instanceof CalendarYearUser)) {
            return false;
        }
        return id != null && id.equals(((CalendarYearUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CalendarYearUser{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", year=" + getYear() +
            ", isPublicHoliday='" + isIsPublicHoliday() + "'" +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
