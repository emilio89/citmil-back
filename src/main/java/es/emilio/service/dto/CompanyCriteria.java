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

/**
 * Criteria class for the {@link es.emilio.domain.Company} entity. This class is used
 * in {@link es.emilio.web.rest.CompanyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /companies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompanyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter primaryColor;

    private StringFilter secondaryColor;

    private StringFilter email;

    private StringFilter phone;

    private IntegerFilter maxDayAppointment;

    private IntegerFilter minDayAppointment;

    private DoubleFilter lat;

    private DoubleFilter lng;

    private LongFilter profesionalId;

    private LongFilter calendarYearProfesionalId;

    private LongFilter appointmentId;

    private LongFilter typeServiceId;

    private LongFilter publicHolidayId;

    private LongFilter timeBandId;

    private LongFilter dynamicContentId;

    private LongFilter menuOptionsAvailableId;

    private LongFilter timeBandAvailableProfesionalDayId;

    public CompanyCriteria() {
    }

    public CompanyCriteria(CompanyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.primaryColor = other.primaryColor == null ? null : other.primaryColor.copy();
        this.secondaryColor = other.secondaryColor == null ? null : other.secondaryColor.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.maxDayAppointment = other.maxDayAppointment == null ? null : other.maxDayAppointment.copy();
        this.minDayAppointment = other.minDayAppointment == null ? null : other.minDayAppointment.copy();
        this.lat = other.lat == null ? null : other.lat.copy();
        this.lng = other.lng == null ? null : other.lng.copy();
        this.profesionalId = other.profesionalId == null ? null : other.profesionalId.copy();
        this.calendarYearProfesionalId = other.calendarYearProfesionalId == null ? null : other.calendarYearProfesionalId.copy();
        this.appointmentId = other.appointmentId == null ? null : other.appointmentId.copy();
        this.typeServiceId = other.typeServiceId == null ? null : other.typeServiceId.copy();
        this.publicHolidayId = other.publicHolidayId == null ? null : other.publicHolidayId.copy();
        this.timeBandId = other.timeBandId == null ? null : other.timeBandId.copy();
        this.dynamicContentId = other.dynamicContentId == null ? null : other.dynamicContentId.copy();
        this.menuOptionsAvailableId = other.menuOptionsAvailableId == null ? null : other.menuOptionsAvailableId.copy();
        this.timeBandAvailableProfesionalDayId = other.timeBandAvailableProfesionalDayId == null ? null : other.timeBandAvailableProfesionalDayId.copy();
    }

    @Override
    public CompanyCriteria copy() {
        return new CompanyCriteria(this);
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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(StringFilter primaryColor) {
        this.primaryColor = primaryColor;
    }

    public StringFilter getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(StringFilter secondaryColor) {
        this.secondaryColor = secondaryColor;
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

    public IntegerFilter getMaxDayAppointment() {
        return maxDayAppointment;
    }

    public void setMaxDayAppointment(IntegerFilter maxDayAppointment) {
        this.maxDayAppointment = maxDayAppointment;
    }

    public IntegerFilter getMinDayAppointment() {
        return minDayAppointment;
    }

    public void setMinDayAppointment(IntegerFilter minDayAppointment) {
        this.minDayAppointment = minDayAppointment;
    }

    public DoubleFilter getLat() {
        return lat;
    }

    public void setLat(DoubleFilter lat) {
        this.lat = lat;
    }

    public DoubleFilter getLng() {
        return lng;
    }

    public void setLng(DoubleFilter lng) {
        this.lng = lng;
    }

    public LongFilter getProfesionalId() {
        return profesionalId;
    }

    public void setProfesionalId(LongFilter profesionalId) {
        this.profesionalId = profesionalId;
    }

    public LongFilter getCalendarYearProfesionalId() {
        return calendarYearProfesionalId;
    }

    public void setCalendarYearProfesionalId(LongFilter calendarYearProfesionalId) {
        this.calendarYearProfesionalId = calendarYearProfesionalId;
    }

    public LongFilter getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(LongFilter appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LongFilter getTypeServiceId() {
        return typeServiceId;
    }

    public void setTypeServiceId(LongFilter typeServiceId) {
        this.typeServiceId = typeServiceId;
    }

    public LongFilter getPublicHolidayId() {
        return publicHolidayId;
    }

    public void setPublicHolidayId(LongFilter publicHolidayId) {
        this.publicHolidayId = publicHolidayId;
    }

    public LongFilter getTimeBandId() {
        return timeBandId;
    }

    public void setTimeBandId(LongFilter timeBandId) {
        this.timeBandId = timeBandId;
    }

    public LongFilter getDynamicContentId() {
        return dynamicContentId;
    }

    public void setDynamicContentId(LongFilter dynamicContentId) {
        this.dynamicContentId = dynamicContentId;
    }

    public LongFilter getMenuOptionsAvailableId() {
        return menuOptionsAvailableId;
    }

    public void setMenuOptionsAvailableId(LongFilter menuOptionsAvailableId) {
        this.menuOptionsAvailableId = menuOptionsAvailableId;
    }

    public LongFilter getTimeBandAvailableProfesionalDayId() {
        return timeBandAvailableProfesionalDayId;
    }

    public void setTimeBandAvailableProfesionalDayId(LongFilter timeBandAvailableProfesionalDayId) {
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
        final CompanyCriteria that = (CompanyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(primaryColor, that.primaryColor) &&
            Objects.equals(secondaryColor, that.secondaryColor) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(maxDayAppointment, that.maxDayAppointment) &&
            Objects.equals(minDayAppointment, that.minDayAppointment) &&
            Objects.equals(lat, that.lat) &&
            Objects.equals(lng, that.lng) &&
            Objects.equals(profesionalId, that.profesionalId) &&
            Objects.equals(calendarYearProfesionalId, that.calendarYearProfesionalId) &&
            Objects.equals(appointmentId, that.appointmentId) &&
            Objects.equals(typeServiceId, that.typeServiceId) &&
            Objects.equals(publicHolidayId, that.publicHolidayId) &&
            Objects.equals(timeBandId, that.timeBandId) &&
            Objects.equals(dynamicContentId, that.dynamicContentId) &&
            Objects.equals(menuOptionsAvailableId, that.menuOptionsAvailableId) &&
            Objects.equals(timeBandAvailableProfesionalDayId, that.timeBandAvailableProfesionalDayId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        primaryColor,
        secondaryColor,
        email,
        phone,
        maxDayAppointment,
        minDayAppointment,
        lat,
        lng,
        profesionalId,
        calendarYearProfesionalId,
        appointmentId,
        typeServiceId,
        publicHolidayId,
        timeBandId,
        dynamicContentId,
        menuOptionsAvailableId,
        timeBandAvailableProfesionalDayId
        );
    }

    @Override
    public String toString() {
        return "CompanyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (primaryColor != null ? "primaryColor=" + primaryColor + ", " : "") +
                (secondaryColor != null ? "secondaryColor=" + secondaryColor + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (maxDayAppointment != null ? "maxDayAppointment=" + maxDayAppointment + ", " : "") +
                (minDayAppointment != null ? "minDayAppointment=" + minDayAppointment + ", " : "") +
                (lat != null ? "lat=" + lat + ", " : "") +
                (lng != null ? "lng=" + lng + ", " : "") +
                (profesionalId != null ? "profesionalId=" + profesionalId + ", " : "") +
                (calendarYearProfesionalId != null ? "calendarYearProfesionalId=" + calendarYearProfesionalId + ", " : "") +
                (appointmentId != null ? "appointmentId=" + appointmentId + ", " : "") +
                (typeServiceId != null ? "typeServiceId=" + typeServiceId + ", " : "") +
                (publicHolidayId != null ? "publicHolidayId=" + publicHolidayId + ", " : "") +
                (timeBandId != null ? "timeBandId=" + timeBandId + ", " : "") +
                (dynamicContentId != null ? "dynamicContentId=" + dynamicContentId + ", " : "") +
                (menuOptionsAvailableId != null ? "menuOptionsAvailableId=" + menuOptionsAvailableId + ", " : "") +
                (timeBandAvailableProfesionalDayId != null ? "timeBandAvailableProfesionalDayId=" + timeBandAvailableProfesionalDayId + ", " : "") +
            "}";
    }

}
