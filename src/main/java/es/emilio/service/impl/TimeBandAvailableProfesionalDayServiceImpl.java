package es.emilio.service.impl;

import es.emilio.service.TimeBandAvailableProfesionalDayService;
import es.emilio.domain.TimeBandAvailableProfesionalDay;
import es.emilio.repository.TimeBandAvailableProfesionalDayRepository;
import es.emilio.service.dto.TimeBandAvailableProfesionalDayDTO;
import es.emilio.service.mapper.TimeBandAvailableProfesionalDayMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TimeBandAvailableProfesionalDay}.
 */
@Service
@Transactional
public class TimeBandAvailableProfesionalDayServiceImpl implements TimeBandAvailableProfesionalDayService {

    private final Logger log = LoggerFactory.getLogger(TimeBandAvailableProfesionalDayServiceImpl.class);

    private final TimeBandAvailableProfesionalDayRepository timeBandAvailableProfesionalDayRepository;

    private final TimeBandAvailableProfesionalDayMapper timeBandAvailableProfesionalDayMapper;

    public TimeBandAvailableProfesionalDayServiceImpl(TimeBandAvailableProfesionalDayRepository timeBandAvailableProfesionalDayRepository, TimeBandAvailableProfesionalDayMapper timeBandAvailableProfesionalDayMapper) {
        this.timeBandAvailableProfesionalDayRepository = timeBandAvailableProfesionalDayRepository;
        this.timeBandAvailableProfesionalDayMapper = timeBandAvailableProfesionalDayMapper;
    }

    /**
     * Save a timeBandAvailableProfesionalDay.
     *
     * @param timeBandAvailableProfesionalDayDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TimeBandAvailableProfesionalDayDTO save(TimeBandAvailableProfesionalDayDTO timeBandAvailableProfesionalDayDTO) {
        log.debug("Request to save TimeBandAvailableProfesionalDay : {}", timeBandAvailableProfesionalDayDTO);
        TimeBandAvailableProfesionalDay timeBandAvailableProfesionalDay = timeBandAvailableProfesionalDayMapper.toEntity(timeBandAvailableProfesionalDayDTO);
        timeBandAvailableProfesionalDay = timeBandAvailableProfesionalDayRepository.save(timeBandAvailableProfesionalDay);
        return timeBandAvailableProfesionalDayMapper.toDto(timeBandAvailableProfesionalDay);
    }

    /**
     * Get all the timeBandAvailableProfesionalDays.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TimeBandAvailableProfesionalDayDTO> findAll() {
        log.debug("Request to get all TimeBandAvailableProfesionalDays");
        return timeBandAvailableProfesionalDayRepository.findAll().stream()
            .map(timeBandAvailableProfesionalDayMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one timeBandAvailableProfesionalDay by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TimeBandAvailableProfesionalDayDTO> findOne(Long id) {
        log.debug("Request to get TimeBandAvailableProfesionalDay : {}", id);
        return timeBandAvailableProfesionalDayRepository.findById(id)
            .map(timeBandAvailableProfesionalDayMapper::toDto);
    }

    /**
     * Delete the timeBandAvailableProfesionalDay by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TimeBandAvailableProfesionalDay : {}", id);
        timeBandAvailableProfesionalDayRepository.deleteById(id);
    }
}
