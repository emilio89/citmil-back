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
 * Criteria class for the {@link es.emilio.domain.TimeBand} entity. This class is used
 * in {@link es.emilio.web.rest.TimeBandResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /time-bands?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TimeBandCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter start;

    private InstantFilter end;

    private LongFilter calendarYearProfesionalId;

    private LongFilter companyId;

    public TimeBandCriteria() {
    }

    public TimeBandCriteria(TimeBandCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.start = other.start == null ? null : other.start.copy();
        this.end = other.end == null ? null : other.end.copy();
        this.calendarYearProfesionalId = other.calendarYearProfesionalId == null ? null : other.calendarYearProfesionalId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
    }

    @Override
    public TimeBandCriteria copy() {
        return new TimeBandCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public LongFilter getCalendarYearProfesionalId() {
        return calendarYearProfesionalId;
    }

    public void setCalendarYearProfesionalId(LongFilter calendarYearProfesionalId) {
        this.calendarYearProfesionalId = calendarYearProfesionalId;
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
        final TimeBandCriteria that = (TimeBandCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(start, that.start) &&
            Objects.equals(end, that.end) &&
            Objects.equals(calendarYearProfesionalId, that.calendarYearProfesionalId) &&
            Objects.equals(companyId, that.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        start,
        end,
        calendarYearProfesionalId,
        companyId
        );
    }

    @Override
    public String toString() {
        return "TimeBandCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (start != null ? "start=" + start + ", " : "") +
                (end != null ? "end=" + end + ", " : "") +
                (calendarYearProfesionalId != null ? "calendarYearProfesionalId=" + calendarYearProfesionalId + ", " : "") +
                (companyId != null ? "companyId=" + companyId + ", " : "") +
            "}";
    }

}
