package es.emilio.repository;

import es.emilio.domain.DynamicContent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DynamicContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DynamicContentRepository extends JpaRepository<DynamicContent, Long>, JpaSpecificationExecutor<DynamicContent> {
}
