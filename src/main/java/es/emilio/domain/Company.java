package es.emilio.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Size(max = 10)
    @Column(name = "primary_color", length = 10)
    private String primaryColor;

    @Size(max = 10)
    @Column(name = "secondary_color", length = 10)
    private String secondaryColor;

    @Lob
    @Column(name = "url_img")
    private byte[] urlImg;

    @Column(name = "url_img_content_type")
    private String urlImgContentType;

    @NotNull
    @Size(max = 150)
    @Column(name = "email", length = 150, nullable = false)
    private String email;

    @NotNull
    @Size(max = 10)
    @Column(name = "phone", length = 10, nullable = false)
    private String phone;

    @Column(name = "max_day_appointment")
    private Integer maxDayAppointment;

    @Column(name = "min_day_appointment")
    private Integer minDayAppointment;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    @OneToMany(mappedBy = "company")
    private Set<Profesional> profesionals = new HashSet<>();

    @OneToMany(mappedBy = "company")
    private Set<CalendarYearProfesional> calendarYearProfesionals = new HashSet<>();

    @OneToMany(mappedBy = "company")
    private Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "company")
    private Set<TypeService> typeServices = new HashSet<>();

    @OneToMany(mappedBy = "company")
    private Set<PublicHoliday> publicHolidays = new HashSet<>();

    @OneToMany(mappedBy = "company")
    private Set<TimeBand> timeBands = new HashSet<>();

    @OneToMany(mappedBy = "company")
    private Set<DynamicContent> dynamicContents = new HashSet<>();

    @OneToMany(mappedBy = "company")
    private Set<MenuOptionsAvailable> menuOptionsAvailables = new HashSet<>();

    @OneToMany(mappedBy = "company")
    private Set<TimeBandAvailableProfesionalDay> timeBandAvailableProfesionalDays = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Company description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public Company primaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
        return this;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public Company secondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
        return this;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public byte[] getUrlImg() {
        return urlImg;
    }

    public Company urlImg(byte[] urlImg) {
        this.urlImg = urlImg;
        return this;
    }

    public void setUrlImg(byte[] urlImg) {
        this.urlImg = urlImg;
    }

    public String getUrlImgContentType() {
        return urlImgContentType;
    }

    public Company urlImgContentType(String urlImgContentType) {
        this.urlImgContentType = urlImgContentType;
        return this;
    }

    public void setUrlImgContentType(String urlImgContentType) {
        this.urlImgContentType = urlImgContentType;
    }

    public String getEmail() {
        return email;
    }

    public Company email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Company phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getMaxDayAppointment() {
        return maxDayAppointment;
    }

    public Company maxDayAppointment(Integer maxDayAppointment) {
        this.maxDayAppointment = maxDayAppointment;
        return this;
    }

    public void setMaxDayAppointment(Integer maxDayAppointment) {
        this.maxDayAppointment = maxDayAppointment;
    }

    public Integer getMinDayAppointment() {
        return minDayAppointment;
    }

    public Company minDayAppointment(Integer minDayAppointment) {
        this.minDayAppointment = minDayAppointment;
        return this;
    }

    public void setMinDayAppointment(Integer minDayAppointment) {
        this.minDayAppointment = minDayAppointment;
    }

    public Double getLat() {
        return lat;
    }

    public Company lat(Double lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public Company lng(Double lng) {
        this.lng = lng;
        return this;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Set<Profesional> getProfesionals() {
        return profesionals;
    }

    public Company profesionals(Set<Profesional> profesionals) {
        this.profesionals = profesionals;
        return this;
    }

    public Company addProfesional(Profesional profesional) {
        this.profesionals.add(profesional);
        profesional.setCompany(this);
        return this;
    }

    public Company removeProfesional(Profesional profesional) {
        this.profesionals.remove(profesional);
        profesional.setCompany(null);
        return this;
    }

    public void setProfesionals(Set<Profesional> profesionals) {
        this.profesionals = profesionals;
    }

    public Set<CalendarYearProfesional> getCalendarYearProfesionals() {
        return calendarYearProfesionals;
    }

    public Company calendarYearProfesionals(Set<CalendarYearProfesional> calendarYearProfesionals) {
        this.calendarYearProfesionals = calendarYearProfesionals;
        return this;
    }

    public Company addCalendarYearProfesional(CalendarYearProfesional calendarYearProfesional) {
        this.calendarYearProfesionals.add(calendarYearProfesional);
        calendarYearProfesional.setCompany(this);
        return this;
    }

    public Company removeCalendarYearProfesional(CalendarYearProfesional calendarYearProfesional) {
        this.calendarYearProfesionals.remove(calendarYearProfesional);
        calendarYearProfesional.setCompany(null);
        return this;
    }

    public void setCalendarYearProfesionals(Set<CalendarYearProfesional> calendarYearProfesionals) {
        this.calendarYearProfesionals = calendarYearProfesionals;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public Company appointments(Set<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }

    public Company addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
        appointment.setCompany(this);
        return this;
    }

    public Company removeAppointment(Appointment appointment) {
        this.appointments.remove(appointment);
        appointment.setCompany(null);
        return this;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Set<TypeService> getTypeServices() {
        return typeServices;
    }

    public Company typeServices(Set<TypeService> typeServices) {
        this.typeServices = typeServices;
        return this;
    }

    public Company addTypeService(TypeService typeService) {
        this.typeServices.add(typeService);
        typeService.setCompany(this);
        return this;
    }

    public Company removeTypeService(TypeService typeService) {
        this.typeServices.remove(typeService);
        typeService.setCompany(null);
        return this;
    }

    public void setTypeServices(Set<TypeService> typeServices) {
        this.typeServices = typeServices;
    }

    public Set<PublicHoliday> getPublicHolidays() {
        return publicHolidays;
    }

    public Company publicHolidays(Set<PublicHoliday> publicHolidays) {
        this.publicHolidays = publicHolidays;
        return this;
    }

    public Company addPublicHoliday(PublicHoliday publicHoliday) {
        this.publicHolidays.add(publicHoliday);
        publicHoliday.setCompany(this);
        return this;
    }

    public Company removePublicHoliday(PublicHoliday publicHoliday) {
        this.publicHolidays.remove(publicHoliday);
        publicHoliday.setCompany(null);
        return this;
    }

    public void setPublicHolidays(Set<PublicHoliday> publicHolidays) {
        this.publicHolidays = publicHolidays;
    }

    public Set<TimeBand> getTimeBands() {
        return timeBands;
    }

    public Company timeBands(Set<TimeBand> timeBands) {
        this.timeBands = timeBands;
        return this;
    }

    public Company addTimeBand(TimeBand timeBand) {
        this.timeBands.add(timeBand);
        timeBand.setCompany(this);
        return this;
    }

    public Company removeTimeBand(TimeBand timeBand) {
        this.timeBands.remove(timeBand);
        timeBand.setCompany(null);
        return this;
    }

    public void setTimeBands(Set<TimeBand> timeBands) {
        this.timeBands = timeBands;
    }

    public Set<DynamicContent> getDynamicContents() {
        return dynamicContents;
    }

    public Company dynamicContents(Set<DynamicContent> dynamicContents) {
        this.dynamicContents = dynamicContents;
        return this;
    }

    public Company addDynamicContent(DynamicContent dynamicContent) {
        this.dynamicContents.add(dynamicContent);
        dynamicContent.setCompany(this);
        return this;
    }

    public Company removeDynamicContent(DynamicContent dynamicContent) {
        this.dynamicContents.remove(dynamicContent);
        dynamicContent.setCompany(null);
        return this;
    }

    public void setDynamicContents(Set<DynamicContent> dynamicContents) {
        this.dynamicContents = dynamicContents;
    }

    public Set<MenuOptionsAvailable> getMenuOptionsAvailables() {
        return menuOptionsAvailables;
    }

    public Company menuOptionsAvailables(Set<MenuOptionsAvailable> menuOptionsAvailables) {
        this.menuOptionsAvailables = menuOptionsAvailables;
        return this;
    }

    public Company addMenuOptionsAvailable(MenuOptionsAvailable menuOptionsAvailable) {
        this.menuOptionsAvailables.add(menuOptionsAvailable);
        menuOptionsAvailable.setCompany(this);
        return this;
    }

    public Company removeMenuOptionsAvailable(MenuOptionsAvailable menuOptionsAvailable) {
        this.menuOptionsAvailables.remove(menuOptionsAvailable);
        menuOptionsAvailable.setCompany(null);
        return this;
    }

    public void setMenuOptionsAvailables(Set<MenuOptionsAvailable> menuOptionsAvailables) {
        this.menuOptionsAvailables = menuOptionsAvailables;
    }

    public Set<TimeBandAvailableProfesionalDay> getTimeBandAvailableProfesionalDays() {
        return timeBandAvailableProfesionalDays;
    }

    public Company timeBandAvailableProfesionalDays(Set<TimeBandAvailableProfesionalDay> timeBandAvailableProfesionalDays) {
        this.timeBandAvailableProfesionalDays = timeBandAvailableProfesionalDays;
        return this;
    }

    public Company addTimeBandAvailableProfesionalDay(TimeBandAvailableProfesionalDay timeBandAvailableProfesionalDay) {
        this.timeBandAvailableProfesionalDays.add(timeBandAvailableProfesionalDay);
        timeBandAvailableProfesionalDay.setCompany(this);
        return this;
    }

    public Company removeTimeBandAvailableProfesionalDay(TimeBandAvailableProfesionalDay timeBandAvailableProfesionalDay) {
        this.timeBandAvailableProfesionalDays.remove(timeBandAvailableProfesionalDay);
        timeBandAvailableProfesionalDay.setCompany(null);
        return this;
    }

    public void setTimeBandAvailableProfesionalDays(Set<TimeBandAvailableProfesionalDay> timeBandAvailableProfesionalDays) {
        this.timeBandAvailableProfesionalDays = timeBandAvailableProfesionalDays;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", primaryColor='" + getPrimaryColor() + "'" +
            ", secondaryColor='" + getSecondaryColor() + "'" +
            ", urlImg='" + getUrlImg() + "'" +
            ", urlImgContentType='" + getUrlImgContentType() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", maxDayAppointment=" + getMaxDayAppointment() +
            ", minDayAppointment=" + getMinDayAppointment() +
            ", lat=" + getLat() +
            ", lng=" + getLng() +
            "}";
    }
}
