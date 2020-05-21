package es.emilio.service.impl;

import es.emilio.service.TypeServiceService;
import es.emilio.domain.TypeService;
import es.emilio.repository.TypeServiceRepository;
import es.emilio.service.dto.TypeServiceDTO;
import es.emilio.service.mapper.TypeServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeService}.
 */
@Service
@Transactional
public class TypeServiceServiceImpl implements TypeServiceService {

    private final Logger log = LoggerFactory.getLogger(TypeServiceServiceImpl.class);

    private final TypeServiceRepository typeServiceRepository;

    private final TypeServiceMapper typeServiceMapper;

    public TypeServiceServiceImpl(TypeServiceRepository typeServiceRepository, TypeServiceMapper typeServiceMapper) {
        this.typeServiceRepository = typeServiceRepository;
        this.typeServiceMapper = typeServiceMapper;
    }

    /**
     * Save a typeService.
     *
     * @param typeServiceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeServiceDTO save(TypeServiceDTO typeServiceDTO) {
        log.debug("Request to save TypeService : {}", typeServiceDTO);
        TypeService typeService = typeServiceMapper.toEntity(typeServiceDTO);
        typeService = typeServiceRepository.save(typeService);
        return typeServiceMapper.toDto(typeService);
    }

    /**
     * Get all the typeServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeServiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeServices");
        return typeServiceRepository.findAll(pageable)
            .map(typeServiceMapper::toDto);
    }

    /**
     * Get one typeService by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeServiceDTO> findOne(Long id) {
        log.debug("Request to get TypeService : {}", id);
        return typeServiceRepository.findById(id)
            .map(typeServiceMapper::toDto);
    }

    /**
     * Delete the typeService by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeService : {}", id);
        typeServiceRepository.deleteById(id);
    }
}
