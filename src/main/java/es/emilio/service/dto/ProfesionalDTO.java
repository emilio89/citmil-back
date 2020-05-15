package es.emilio.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link es.emilio.domain.Profesional} entity.
 */
public class ProfesionalDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 200)
    private String firstName;

    @Size(max = 255)
    private String lastName;

    private String description;

    @Size(max = 200)
    private String email;

    @Size(max = 255)
    private String address;

    @Size(max = 20)
    private String phone;

    private LocalDate birthdate;

    @Lob
    private byte[] urlImg;

    private String urlImgContentType;
    private Boolean actived;

    private Boolean deleted;

    private Set<TypeServiceDTO> typeServices = new HashSet<>();

    private Long calendarYearProfesionalId;

    private Long companyId;

    private Long timeBandAvailableProfesionalDayId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public byte[] getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(byte[] urlImg) {
        this.urlImg = urlImg;
    }

    public String getUrlImgContentType() {
        return urlImgContentType;
    }

    public void setUrlImgContentType(String urlImgContentType) {
        this.urlImgContentType = urlImgContentType;
    }

    public Boolean isActived() {
        return actived;
    }

    public void setActived(Boolean actived) {
        this.actived = actived;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Set<TypeServiceDTO> getTypeServices() {
        return typeServices;
    }

    public void setTypeServices(Set<TypeServiceDTO> typeServices) {
        this.typeServices = typeServices;
    }

    public Long getCalendarYearProfesionalId() {
        return calendarYearProfesionalId;
    }

    public void setCalendarYearProfesionalId(Long calendarYearProfesionalId) {
        this.calendarYearProfesionalId = calendarYearProfesionalId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getTimeBandAvailableProfesionalDayId() {
        return timeBandAvailableProfesionalDayId;
    }

    public void setTimeBandAvailableProfesionalDayId(Long timeBandAvailableProfesionalDayId) {
        this.timeBandAvailableProfesionalDayId = timeBandAvailableProfesionalDayId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfesionalDTO profesionalDTO = (ProfesionalDTO) o;
        if (profesionalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profesionalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfesionalDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", description='" + getDescription() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", phone='" + getPhone() + "'" +
            ", birthdate='" + getBirthdate() + "'" +
            ", urlImg='" + getUrlImg() + "'" +
            ", actived='" + isActived() + "'" +
            ", deleted='" + isDeleted() + "'" +
            ", typeServices='" + getTypeServices() + "'" +
            ", calendarYearProfesionalId=" + getCalendarYearProfesionalId() +
            ", companyId=" + getCompanyId() +
            ", timeBandAvailableProfesionalDayId=" + getTimeBandAvailableProfesionalDayId() +
            "}";
    }
}
