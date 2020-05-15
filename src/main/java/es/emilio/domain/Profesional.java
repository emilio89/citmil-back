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
 * A Profesional.
 */
@Entity
@Table(name = "profesional")
public class Profesional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "first_name", length = 200, nullable = false)
    private String firstName;

    @Size(max = 255)
    @Column(name = "last_name", length = 255)
    private String lastName;

    @Column(name = "description")
    private String description;

    @Size(max = 200)
    @Column(name = "email", length = 200, unique = true)
    private String email;

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

    @OneToMany(mappedBy = "profesional")
    private Set<Appointment> appointments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "profesional_type_service",
               joinColumns = @JoinColumn(name = "profesional_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "type_service_id", referencedColumnName = "id"))
    private Set<TypeService> typeServices = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("profesionals")
    private CalendarYearProfesional calendarYearProfesional;

    @ManyToOne
    @JsonIgnoreProperties("profesionals")
    private Company company;

    @ManyToOne
    @JsonIgnoreProperties("profesionals")
    private TimeBandAvailableProfesionalDay timeBandAvailableProfesionalDay;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Profesional firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Profesional lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public Profesional description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public Profesional email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public Profesional address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public Profesional phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public Profesional birthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public byte[] getUrlImg() {
        return urlImg;
    }

    public Profesional urlImg(byte[] urlImg) {
        this.urlImg = urlImg;
        return this;
    }

    public void setUrlImg(byte[] urlImg) {
        this.urlImg = urlImg;
    }

    public String getUrlImgContentType() {
        return urlImgContentType;
    }

    public Profesional urlImgContentType(String urlImgContentType) {
        this.urlImgContentType = urlImgContentType;
        return this;
    }

    public void setUrlImgContentType(String urlImgContentType) {
        this.urlImgContentType = urlImgContentType;
    }

    public Boolean isActived() {
        return actived;
    }

    public Profesional actived(Boolean actived) {
        this.actived = actived;
        return this;
    }

    public void setActived(Boolean actived) {
        this.actived = actived;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Profesional deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public Profesional appointments(Set<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }

    public Profesional addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
        appointment.setProfesional(this);
        return this;
    }

    public Profesional removeAppointment(Appointment appointment) {
        this.appointments.remove(appointment);
        appointment.setProfesional(null);
        return this;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Set<TypeService> getTypeServices() {
        return typeServices;
    }

    public Profesional typeServices(Set<TypeService> typeServices) {
        this.typeServices = typeServices;
        return this;
    }

    public Profesional addTypeService(TypeService typeService) {
        this.typeServices.add(typeService);
        typeService.getProfesionals().add(this);
        return this;
    }

    public Profesional removeTypeService(TypeService typeService) {
        this.typeServices.remove(typeService);
        typeService.getProfesionals().remove(this);
        return this;
    }

    public void setTypeServices(Set<TypeService> typeServices) {
        this.typeServices = typeServices;
    }

    public CalendarYearProfesional getCalendarYearProfesional() {
        return calendarYearProfesional;
    }

    public Profesional calendarYearProfesional(CalendarYearProfesional calendarYearProfesional) {
        this.calendarYearProfesional = calendarYearProfesional;
        return this;
    }

    public void setCalendarYearProfesional(CalendarYearProfesional calendarYearProfesional) {
        this.calendarYearProfesional = calendarYearProfesional;
    }

    public Company getCompany() {
        return company;
    }

    public Profesional company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public TimeBandAvailableProfesionalDay getTimeBandAvailableProfesionalDay() {
        return timeBandAvailableProfesionalDay;
    }

    public Profesional timeBandAvailableProfesionalDay(TimeBandAvailableProfesionalDay timeBandAvailableProfesionalDay) {
        this.timeBandAvailableProfesionalDay = timeBandAvailableProfesionalDay;
        return this;
    }

    public void setTimeBandAvailableProfesionalDay(TimeBandAvailableProfesionalDay timeBandAvailableProfesionalDay) {
        this.timeBandAvailableProfesionalDay = timeBandAvailableProfesionalDay;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profesional)) {
            return false;
        }
        return id != null && id.equals(((Profesional) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Profesional{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", description='" + getDescription() + "'" +
            ", email='" + getEmail() + "'" +
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
