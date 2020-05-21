package es.emilio.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DynamicContent.
 */
@Entity
@Table(name = "dynamic_content")
public class DynamicContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "url_img")
    private byte[] urlImg;

    @Column(name = "url_img_content_type")
    private String urlImgContentType;

    @Column(name = "actived")
    private Boolean actived;

    @ManyToOne
    @JsonIgnoreProperties("dynamicContents")
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public DynamicContent title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public DynamicContent description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getUrlImg() {
        return urlImg;
    }

    public DynamicContent urlImg(byte[] urlImg) {
        this.urlImg = urlImg;
        return this;
    }

    public void setUrlImg(byte[] urlImg) {
        this.urlImg = urlImg;
    }

    public String getUrlImgContentType() {
        return urlImgContentType;
    }

    public DynamicContent urlImgContentType(String urlImgContentType) {
        this.urlImgContentType = urlImgContentType;
        return this;
    }

    public void setUrlImgContentType(String urlImgContentType) {
        this.urlImgContentType = urlImgContentType;
    }

    public Boolean isActived() {
        return actived;
    }

    public DynamicContent actived(Boolean actived) {
        this.actived = actived;
        return this;
    }

    public void setActived(Boolean actived) {
        this.actived = actived;
    }

    public Company getCompany() {
        return company;
    }

    public DynamicContent company(Company company) {
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
        if (!(o instanceof DynamicContent)) {
            return false;
        }
        return id != null && id.equals(((DynamicContent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DynamicContent{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", urlImg='" + getUrlImg() + "'" +
            ", urlImgContentType='" + getUrlImgContentType() + "'" +
            ", actived='" + isActived() + "'" +
            "}";
    }
}
