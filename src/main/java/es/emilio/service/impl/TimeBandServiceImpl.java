package es.emilio.service.impl;

import es.emilio.service.TimeBandService;
import es.emilio.domain.TimeBand;
import es.emilio.repository.TimeBandRepository;
import es.emilio.service.dto.TimeBandDTO;
import es.emilio.service.mapper.TimeBandMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TimeBand}.
 */
@Service
@Transactional
public class TimeBandServiceImpl implements TimeBandService {

    private final Logger log = LoggerFactory.getLogger(TimeBandServiceImpl.class);

    private final TimeBandRepository timeBandRepository;

    private final TimeBandMapper timeBandMapper;

    public TimeBandServiceImpl(TimeBandRepository timeBandRepository, TimeBandMapper timeBandMapper) {
        this.timeBandRepository = timeBandRepository;
        this.timeBandMapper = timeBandMapper;
    }

    /**
     * Save a timeBand.
     *
     * @param timeBandDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TimeBandDTO save(TimeBandDTO timeBandDTO) {
        log.debug("Request to save TimeBand : {}", timeBandDTO);
        TimeBand timeBand = timeBandMapper.toEntity(timeBandDTO);
        timeBand = timeBandRepository.save(timeBand);
        return timeBandMapper.toDto(timeBand);
    }

    /**
     * Get all the timeBands.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TimeBandDTO> findAll() {
        log.debug("Request to get all TimeBands");
        return timeBandRepository.findAllWithEagerRelationships().stream()
            .map(timeBandMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the timeBands with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TimeBandDTO> findAllWithEagerRelationships(Pageable pageable) {
        return timeBandRepository.findAllWithEagerRelationships(pageable).map(timeBandMapper::toDto);
    }

    /**
     * Get one timeBand by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TimeBandDTO> findOne(Long id) {
        log.debug("Request to get TimeBand : {}", id);
        return timeBandRepository.findOneWithEagerRelationships(id)
            .map(timeBandMapper::toDto);
    }

    /**
     * Delete the timeBand by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TimeBand : {}", id);
        timeBandRepository.deleteById(id);
    }
}
