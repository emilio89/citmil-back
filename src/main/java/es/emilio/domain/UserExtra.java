package es.emilio.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A UserExtra.
 */
@Entity
@Table(name = "user_extra")
public class UserExtra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Size(max = 255)
    @Column(name = "address", length = 255)
    private String address;

    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Lob
    @Column(name = "url_img")
    private byte[] urlImg;

    @Column(name = "url_img_content_type")
    private String urlImgContentType;

    @Column(name = "actived")
    private Boolean actived;

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true, nullable = false)
    private User user;

    @OneToMany(mappedBy = "userExtra")
    private Set<Appointment> appointments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_extra_type_service",
               joinColumns = @JoinColumn(name = "user_extra_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "type_service_id", referencedColumnName = "id"))
    private Set<TypeService> typeServices = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("userExtras")
    private CalendarYearUser calendarYearUser;

    @ManyToOne
    @JsonIgnoreProperties("userExtras")
    private Company company;

    @ManyToOne
    @JsonIgnoreProperties("userExtras")
    private TimeBandAvailableUserDay timeBandAvailableUserDay;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public UserExtra description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public UserExtra address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public UserExtra phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public UserExtra birthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public byte[] getUrlImg() {
        return urlImg;
    }

    public UserExtra urlImg(byte[] urlImg) {
        this.urlImg = urlImg;
        return this;
    }

    public void setUrlImg(byte[] urlImg) {
        this.urlImg = urlImg;
    }

    public String getUrlImgContentType() {
        return urlImgContentType;
    }

    public UserExtra urlImgContentType(String urlImgContentType) {
        this.urlImgContentType = urlImgContentType;
        return this;
    }

    public void setUrlImgContentType(String urlImgContentType) {
        this.urlImgContentType = urlImgContentType;
    }

    public Boolean isActived() {
        return actived;
    }

    public UserExtra actived(Boolean actived) {
        this.actived = actived;
        return this;
    }

    public void setActived(Boolean actived) {
        this.actived = actived;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public UserExtra deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public User getUser() {
        return user;
    }

    public UserExtra user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public UserExtra appointments(Set<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }

    public UserExtra addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
        appointment.setUserExtra(this);
        return this;
    }

    public UserExtra removeAppointment(Appointment appointment) {
        this.appointments.remove(appointment);
        appointment.setUserExtra(null);
        return this;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Set<TypeService> getTypeServices() {
        return typeServices;
    }

    public UserExtra typeServices(Set<TypeService> typeServices) {
        this.typeServices = typeServices;
        return this;
    }

    public UserExtra addTypeService(TypeService typeService) {
        this.typeServices.add(typeService);
        typeService.getUserExtras().add(this);
        return this;
    }

    public UserExtra removeTypeService(TypeService typeService) {
        this.typeServices.remove(typeService);
        typeService.getUserExtras().remove(this);
        return this;
    }

    public void setTypeServices(Set<TypeService> typeServices) {
        this.typeServices = typeServices;
    }

    public CalendarYearUser getCalendarYearUser() {
        return calendarYearUser;
    }

    public UserExtra calendarYearUser(CalendarYearUser calendarYearUser) {
        this.calendarYearUser = calendarYearUser;
        return this;
    }

    public void setCalendarYearUser(CalendarYearUser calendarYearUser) {
        this.calendarYearUser = calendarYearUser;
    }

    public Company getCompany() {
        return company;
    }

    public UserExtra company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public TimeBandAvailableUserDay getTimeBandAvailableUserDay() {
        return timeBandAvailableUserDay;
    }

    public UserExtra timeBandAvailableUserDay(TimeBandAvailableUserDay timeBandAvailableUserDay) {
        this.timeBandAvailableUserDay = timeBandAvailableUserDay;
        return this;
    }

    public void setTimeBandAvailableUserDay(TimeBandAvailableUserDay timeBandAvailableUserDay) {
        this.timeBandAvailableUserDay = timeBandAvailableUserDay;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserExtra)) {
            return false;
        }
        return id != null && id.equals(((UserExtra) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserExtra{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", address='" + getAddress() + "'" +
            ", phone='" + getPhone() + "'" +
            ", birthdate='" + getBirthdate() + "'" +
            ", urlImg='" + getUrlImg() + "'" +
            ", urlImgContentType='" + getUrlImgContentType() + "'" +
            ", actived='" + isActived() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
