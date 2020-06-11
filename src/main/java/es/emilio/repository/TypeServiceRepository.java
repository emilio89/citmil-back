package es.emilio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.emilio.domain.TypeService;

/**
 * Spring Data  repository for the TypeService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeServiceRepository extends JpaRepository<TypeService, Long> {
	
	@Query("select ts from TypeService ts where company.id = :companyId")
	Page<TypeService> findAllActivesByCompanyId (Pageable pageable,@Param("companyId") Long companyId);
}
