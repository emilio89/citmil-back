package es.emilio.repository;

import es.emilio.domain.TimeBand;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the TimeBand entity.
 */
@Repository
public interface TimeBandRepository extends JpaRepository<TimeBand, Long>, JpaSpecificationExecutor<TimeBand> {

    @Query(value = "select distinct timeBand from TimeBand timeBand left join fetch timeBand.calendarYearUsers",
        countQuery = "select count(distinct timeBand) from TimeBand timeBand")
    Page<TimeBand> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct timeBand from TimeBand timeBand left join fetch timeBand.calendarYearUsers")
    List<TimeBand> findAllWithEagerRelationships();

    @Query("select timeBand from TimeBand timeBand left join fetch timeBand.calendarYearUsers where timeBand.id =:id")
    Optional<TimeBand> findOneWithEagerRelationships(@Param("id") Long id);
}
