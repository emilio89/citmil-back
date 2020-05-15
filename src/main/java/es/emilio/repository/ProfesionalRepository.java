package es.emilio.repository;

import es.emilio.domain.Profesional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Profesional entity.
 */
@Repository
public interface ProfesionalRepository extends JpaRepository<Profesional, Long> {

    @Query(value = "select distinct profesional from Profesional profesional left join fetch profesional.typeServices",
        countQuery = "select count(distinct profesional) from Profesional profesional")
    Page<Profesional> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct profesional from Profesional profesional left join fetch profesional.typeServices")
    List<Profesional> findAllWithEagerRelationships();

    @Query("select profesional from Profesional profesional left join fetch profesional.typeServices where profesional.id =:id")
    Optional<Profesional> findOneWithEagerRelationships(@Param("id") Long id);
}
