package es.emilio.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link es.emilio.domain.DynamicContent} entity.
 */
public class DynamicContentDTO implements Serializable {
    
    private Long id;

    private String title;

    private String description;

    @Lob
    private byte[] urlImg;

    private String urlImgContentType;
    private Boolean actived;


    private Long companyId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

        DynamicContentDTO dynamicContentDTO = (DynamicContentDTO) o;
        if (dynamicContentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dynamicContentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DynamicContentDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", urlImg='" + getUrlImg() + "'" +
            ", actived='" + isActived() + "'" +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
