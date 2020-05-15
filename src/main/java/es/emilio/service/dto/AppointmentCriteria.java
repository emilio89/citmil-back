package es.emilio.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link es.emilio.domain.Appointment} entity. This class is used
 * in {@link es.emilio.web.rest.AppointmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /appointments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AppointmentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter comments;

    private StringFilter email;

    private StringFilter phone;

    private StringFilter dni;

    private InstantFilter start;

    private InstantFilter end;

    private BooleanFilter actived;

    private LongFilter profesionalId;

    private LongFilter companyId;

    public AppointmentCriteria() {
    }

    public AppointmentCriteria(AppointmentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.dni = other.dni == null ? null : other.dni.copy();
        this.start = other.start == null ? null : other.start.copy();
        this.end = other.end == null ? null : other.end.copy();
        this.actived = other.actived == null ? null : other.actived.copy();
        this.profesionalId = other.profesionalId == null ? null : other.profesionalId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
    }

    @Override
    public AppointmentCriteria copy() {
        return new AppointmentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getDni() {
        return dni;
    }

    public void setDni(StringFilter dni) {
        this.dni = dni;
    }

    public InstantFilter getStart() {
        return start;
    }

    public void setStart(InstantFilter start) {
        this.start = start;
    }

    public InstantFilter getEnd() {
        return end;
    }

    public void setEnd(InstantFilter end) {
        this.end = end;
    }

    public BooleanFilter getActived() {
        return actived;
    }

    public void setActived(BooleanFilter actived) {
        this.actived = actived;
    }

    public LongFilter getProfesionalId() {
        return profesionalId;
    }

    public void setProfesionalId(LongFilter profesionalId) {
        this.profesionalId = profesionalId;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
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
        final AppointmentCriteria that = (AppointmentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(dni, that.dni) &&
            Objects.equals(start, that.start) &&
            Objects.equals(end, that.end) &&
            Objects.equals(actived, that.actived) &&
            Objects.equals(profesionalId, that.profesionalId) &&
            Objects.equals(companyId, that.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        comments,
        email,
        phone,
        dni,
        start,
        end,
        actived,
        profesionalId,
        companyId
        );
    }

    @Override
    public String toString() {
        return "AppointmentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (dni != null ? "dni=" + dni + ", " : "") +
                (start != null ? "start=" + start + ", " : "") +
                (end != null ? "end=" + end + ", " : "") +
                (actived != null ? "actived=" + actived + ", " : "") +
                (profesionalId != null ? "profesionalId=" + profesionalId + ", " : "") +
                (companyId != null ? "companyId=" + companyId + ", " : "") +
            "}";
    }

}
