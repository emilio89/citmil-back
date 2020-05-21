package es.emilio.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link es.emilio.domain.UserExtra} entity.
 */
public class UserExtraDTO implements Serializable {
    
    private Long id;

    private String description;

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


    private Long userId;
    private Set<TypeServiceDTO> typeServices = new HashSet<>();

    private Long calendarYearUserId;

    private Long companyId;

    private Long timeBandAvailableUserDayId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<TypeServiceDTO> getTypeServices() {
        return typeServices;
    }

    public void setTypeServices(Set<TypeServiceDTO> typeServices) {
        this.typeServices = typeServices;
    }

    public Long getCalendarYearUserId() {
        return calendarYearUserId;
    }

    public void setCalendarYearUserId(Long calendarYearUserId) {
        this.calendarYearUserId = calendarYearUserId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getTimeBandAvailableUserDayId() {
        return timeBandAvailableUserDayId;
    }

    public void setTimeBandAvailableUserDayId(Long timeBandAvailableUserDayId) {
        this.timeBandAvailableUserDayId = timeBandAvailableUserDayId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserExtraDTO userExtraDTO = (UserExtraDTO) o;
        if (userExtraDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userExtraDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserExtraDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", address='" + getAddress() + "'" +
            ", phone='" + getPhone() + "'" +
            ", birthdate='" + getBirthdate() + "'" +
            ", urlImg='" + getUrlImg() + "'" +
            ", actived='" + isActived() + "'" +
            ", deleted='" + isDeleted() + "'" +
            ", userId=" + getUserId() +
            ", typeServices='" + getTypeServices() + "'" +
            ", calendarYearUserId=" + getCalendarYearUserId() +
            ", companyId=" + getCompanyId() +
            ", timeBandAvailableUserDayId=" + getTimeBandAvailableUserDayId() +
            "}";
    }
}
