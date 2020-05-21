package es.emilio.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link es.emilio.domain.TypeService} entity.
 */
public class TypeServiceDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 200)
    private String name;

    private String description;

    @NotNull
    private Integer durationMinutes;

    private Integer maxDayAppointment;

    private Integer minDayAppointment;

    private Double price;

    @Lob
    private byte[] icon;

    private String iconContentType;
    private Boolean actived;


    private Long companyId;
    
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

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public String getIconContentType() {
        return iconContentType;
    }

    public void setIconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
    }

    public Boolean isActived() {
        return actived;
    }

    public void setActived(Boolean actived) {
        this.actived = actived;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TypeServiceDTO typeServiceDTO = (TypeServiceDTO) o;
        if (typeServiceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeServiceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeServiceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", durationMinutes=" + getDurationMinutes() +
            ", maxDayAppointment=" + getMaxDayAppointment() +
            ", minDayAppointment=" + getMinDayAppointment() +
            ", price=" + getPrice() +
            ", icon='" + getIcon() + "'" +
            ", actived='" + isActived() + "'" +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
