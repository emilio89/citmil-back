package es.emilio.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeService.
 */
@Entity
@Table(name = "type_service")
public class TypeService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Column(name = "max_day_appointment")
    private Integer maxDayAppointment;

    @Column(name = "min_day_appointment")
    private Integer minDayAppointment;

    @Column(name = "price")
    private Double price;

    @Lob
    @Column(name = "icon")
    private byte[] icon;

    @Column(name = "icon_content_type")
    private String iconContentType;

    @Column(name = "actived")
    private Boolean actived;

    @ManyToOne
    @JsonIgnoreProperties("typeServices")
    private Company company;

    @ManyToMany(mappedBy = "typeServices")
    @JsonIgnore
    private Set<Profesional> profesionals = new HashSet<>();

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

    public TypeService name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public TypeService description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public TypeService durationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
        return this;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Integer getMaxDayAppointment() {
        return maxDayAppointment;
    }

    public TypeService maxDayAppointment(Integer maxDayAppointment) {
        this.maxDayAppointment = maxDayAppointment;
        return this;
    }

    public void setMaxDayAppointment(Integer maxDayAppointment) {
        this.maxDayAppointment = maxDayAppointment;
    }

    public Integer getMinDayAppointment() {
        return minDayAppointment;
    }

    public TypeService minDayAppointment(Integer minDayAppointment) {
        this.minDayAppointment = minDayAppointment;
        return this;
    }

    public void setMinDayAppointment(Integer minDayAppointment) {
        this.minDayAppointment = minDayAppointment;
    }

    public Double getPrice() {
        return price;
    }

    public TypeService price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public byte[] getIcon() {
        return icon;
    }

    public TypeService icon(byte[] icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public String getIconContentType() {
        return iconContentType;
    }

    public TypeService iconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
        return this;
    }

    public void setIconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
    }

    public Boolean isActived() {
        return actived;
    }

    public TypeService actived(Boolean actived) {
        this.actived = actived;
        return this;
    }

    public void setActived(Boolean actived) {
        this.actived = actived;
    }

    public Company getCompany() {
        return company;
    }

    public TypeService company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Profesional> getProfesionals() {
        return profesionals;
    }

    public TypeService profesionals(Set<Profesional> profesionals) {
        this.profesionals = profesionals;
        return this;
    }

    public TypeService addProfesional(Profesional profesional) {
        this.profesionals.add(profesional);
        profesional.getTypeServices().add(this);
        return this;
    }

    public TypeService removeProfesional(Profesional profesional) {
        this.profesionals.remove(profesional);
        profesional.getTypeServices().remove(this);
        return this;
    }

    public void setProfesionals(Set<Profesional> profesionals) {
        this.profesionals = profesionals;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeService)) {
            return false;
        }
        return id != null && id.equals(((TypeService) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeService{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", durationMinutes=" + getDurationMinutes() +
            ", maxDayAppointment=" + getMaxDayAppointment() +
            ", minDayAppointment=" + getMinDayAppointment() +
            ", price=" + getPrice() +
            ", icon='" + getIcon() + "'" +
            ", iconContentType='" + getIconContentType() + "'" +
            ", actived='" + isActived() + "'" +
            "}";
    }
}
