package es.emilio.service.impl;

import es.emilio.service.PublicHolidayService;
import es.emilio.domain.PublicHoliday;
import es.emilio.repository.PublicHolidayRepository;
import es.emilio.service.dto.PublicHolidayDTO;
import es.emilio.service.mapper.PublicHolidayMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PublicHoliday}.
 */
@Service
@Transactional
public class PublicHolidayServiceImpl implements PublicHolidayService {

    private final Logger log = LoggerFactory.getLogger(PublicHolidayServiceImpl.class);

    private final PublicHolidayRepository publicHolidayRepository;

    private final PublicHolidayMapper publicHolidayMapper;

    public PublicHolidayServiceImpl(PublicHolidayRepository publicHolidayRepository, PublicHolidayMapper publicHolidayMapper) {
        this.publicHolidayRepository = publicHolidayRepository;
        this.publicHolidayMapper = publicHolidayMapper;
    }

    /**
     * Save a publicHoliday.
     *
     * @param publicHolidayDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PublicHolidayDTO save(PublicHolidayDTO publicHolidayDTO) {
        log.debug("Request to save PublicHoliday : {}", publicHolidayDTO);
        PublicHoliday publicHoliday = publicHolidayMapper.toEntity(publicHolidayDTO);
        publicHoliday = publicHolidayRepository.save(publicHoliday);
        return publicHolidayMapper.toDto(publicHoliday);
    }

    /**
     * Get all the publicHolidays.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PublicHolidayDTO> findAll() {
        log.debug("Request to get all PublicHolidays");
        return publicHolidayRepository.findAll().stream()
            .map(publicHolidayMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one publicHoliday by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PublicHolidayDTO> findOne(Long id) {
        log.debug("Request to get PublicHoliday : {}", id);
        return publicHolidayRepository.findById(id)
            .map(publicHolidayMapper::toDto);
    }

    /**
     * Delete the publicHoliday by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PublicHoliday : {}", id);
        publicHolidayRepository.deleteById(id);
    }
}
