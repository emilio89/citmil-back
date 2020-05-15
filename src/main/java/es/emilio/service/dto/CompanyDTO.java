package es.emilio.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link es.emilio.domain.Company} entity.
 */
public class CompanyDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private String description;

    @Size(max = 10)
    private String primaryColor;

    @Size(max = 10)
    private String secondaryColor;

    @Lob
    private byte[] urlImg;

    private String urlImgContentType;
    @NotNull
    @Size(max = 150)
    private String email;

    @NotNull
    @Size(max = 10)
    private String phone;

    private Integer maxDayAppointment;

    private Integer minDayAppointment;

    private Double lat;

    private Double lng;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getMaxDayAppointment() {
        return maxDayAppointment;
    }

    public void setMaxDayAppointment(Integer maxDayAppointment) {
        this.maxDayAppointment = maxDayAppointment;
    }

    public Integer getMinDayAppointment() {
        return minDayAppointment;
    }

    public void setMinDayAppointment(Integer minDayAppointment) {
        this.minDayAppointment = minDayAppointment;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyDTO companyDTO = (CompanyDTO) o;
        if (companyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", primaryColor='" + getPrimaryColor() + "'" +
            ", secondaryColor='" + getSecondaryColor() + "'" +
            ", urlImg='" + getUrlImg() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", maxDayAppointment=" + getMaxDayAppointment() +
            ", minDayAppointment=" + getMinDayAppointment() +
            ", lat=" + getLat() +
            ", lng=" + getLng() +
            "}";
    }
}
