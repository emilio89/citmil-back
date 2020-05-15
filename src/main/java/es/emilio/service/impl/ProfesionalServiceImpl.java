package es.emilio.service.impl;

import es.emilio.service.ProfesionalService;
import es.emilio.domain.Profesional;
import es.emilio.repository.ProfesionalRepository;
import es.emilio.service.dto.ProfesionalDTO;
import es.emilio.service.mapper.ProfesionalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Profesional}.
 */
@Service
@Transactional
public class ProfesionalServiceImpl implements ProfesionalService {

    private final Logger log = LoggerFactory.getLogger(ProfesionalServiceImpl.class);

    private final ProfesionalRepository profesionalRepository;

    private final ProfesionalMapper profesionalMapper;

    public ProfesionalServiceImpl(ProfesionalRepository profesionalRepository, ProfesionalMapper profesionalMapper) {
        this.profesionalRepository = profesionalRepository;
        this.profesionalMapper = profesionalMapper;
    }

    /**
     * Save a profesional.
     *
     * @param profesionalDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProfesionalDTO save(ProfesionalDTO profesionalDTO) {
        log.debug("Request to save Profesional : {}", profesionalDTO);
        Profesional profesional = profesionalMapper.toEntity(profesionalDTO);
        profesional = profesionalRepository.save(profesional);
        return profesionalMapper.toDto(profesional);
    }

    /**
     * Get all the profesionals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProfesionalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Profesionals");
        return profesionalRepository.findAll(pageable)
            .map(profesionalMapper::toDto);
    }

    /**
     * Get all the profesionals with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProfesionalDTO> findAllWithEagerRelationships(Pageable pageable) {
        return profesionalRepository.findAllWithEagerRelationships(pageable).map(profesionalMapper::toDto);
    }

    /**
     * Get one profesional by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProfesionalDTO> findOne(Long id) {
        log.debug("Request to get Profesional : {}", id);
        return profesionalRepository.findOneWithEagerRelationships(id)
            .map(profesionalMapper::toDto);
    }

    /**
     * Delete the profesional by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Profesional : {}", id);
        profesionalRepository.deleteById(id);
    }
}
