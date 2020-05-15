package es.emilio.repository;

import es.emilio.domain.MenuOptionsAvailable;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MenuOptionsAvailable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenuOptionsAvailableRepository extends JpaRepository<MenuOptionsAvailable, Long>, JpaSpecificationExecutor<MenuOptionsAvailable> {
}
