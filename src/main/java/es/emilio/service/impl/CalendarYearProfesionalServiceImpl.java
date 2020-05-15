package es.emilio.service.impl;

import es.emilio.service.CalendarYearProfesionalService;
import es.emilio.domain.CalendarYearProfesional;
import es.emilio.repository.CalendarYearProfesionalRepository;
import es.emilio.service.dto.CalendarYearProfesionalDTO;
import es.emilio.service.mapper.CalendarYearProfesionalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CalendarYearProfesional}.
 */
@Service
@Transactional
public class CalendarYearProfesionalServiceImpl implements CalendarYearProfesionalService {

    private final Logger log = LoggerFactory.getLogger(CalendarYearProfesionalServiceImpl.class);

    private final CalendarYearProfesionalRepository calendarYearProfesionalRepository;

    private final CalendarYearProfesionalMapper calendarYearProfesionalMapper;

    public CalendarYearProfesionalServiceImpl(CalendarYearProfesionalRepository calendarYearProfesionalRepository, CalendarYearProfesionalMapper calendarYearProfesionalMapper) {
        this.calendarYearProfesionalRepository = calendarYearProfesionalRepository;
        this.calendarYearProfesionalMapper = calendarYearProfesionalMapper;
    }

    /**
     * Save a calendarYearProfesional.
     *
     * @param calendarYearProfesionalDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CalendarYearProfesionalDTO save(CalendarYearProfesionalDTO calendarYearProfesionalDTO) {
        log.debug("Request to save CalendarYearProfesional : {}", calendarYearProfesionalDTO);
        CalendarYearProfesional calendarYearProfesional = calendarYearProfesionalMapper.toEntity(calendarYearProfesionalDTO);
        calendarYearProfesional = calendarYearProfesionalRepository.save(calendarYearProfesional);
        return calendarYearProfesionalMapper.toDto(calendarYearProfesional);
    }

    /**
     * Get all the calendarYearProfesionals.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CalendarYearProfesionalDTO> findAll() {
        log.debug("Request to get all CalendarYearProfesionals");
        return calendarYearProfesionalRepository.findAll().stream()
            .map(calendarYearProfesionalMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one calendarYearProfesional by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CalendarYearProfesionalDTO> findOne(Long id) {
        log.debug("Request to get CalendarYearProfesional : {}", id);
        return calendarYearProfesionalRepository.findById(id)
            .map(calendarYearProfesionalMapper::toDto);
    }

    /**
     * Delete the calendarYearProfesional by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CalendarYearProfesional : {}", id);
        calendarYearProfesionalRepository.deleteById(id);
    }
}
