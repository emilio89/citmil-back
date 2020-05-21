package es.emilio.repository;

import es.emilio.domain.UserExtra;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the UserExtra entity.
 */
@Repository
public interface UserExtraRepository extends JpaRepository<UserExtra, Long> {

    @Query(value = "select distinct userExtra from UserExtra userExtra left join fetch userExtra.typeServices",
        countQuery = "select count(distinct userExtra) from UserExtra userExtra")
    Page<UserExtra> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct userExtra from UserExtra userExtra left join fetch userExtra.typeServices")
    List<UserExtra> findAllWithEagerRelationships();

    @Query("select userExtra from UserExtra userExtra left join fetch userExtra.typeServices where userExtra.id =:id")
    Optional<UserExtra> findOneWithEagerRelationships(@Param("id") Long id);
}
