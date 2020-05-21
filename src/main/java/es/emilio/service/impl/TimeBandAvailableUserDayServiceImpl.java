package es.emilio.service.impl;

import es.emilio.service.TimeBandAvailableUserDayService;
import es.emilio.domain.TimeBandAvailableUserDay;
import es.emilio.repository.TimeBandAvailableUserDayRepository;
import es.emilio.service.dto.TimeBandAvailableUserDayDTO;
import es.emilio.service.mapper.TimeBandAvailableUserDayMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TimeBandAvailableUserDay}.
 */
@Service
@Transactional
public class TimeBandAvailableUserDayServiceImpl implements TimeBandAvailableUserDayService {

    private final Logger log = LoggerFactory.getLogger(TimeBandAvailableUserDayServiceImpl.class);

    private final TimeBandAvailableUserDayRepository timeBandAvailableUserDayRepository;

    private final TimeBandAvailableUserDayMapper timeBandAvailableUserDayMapper;

    public TimeBandAvailableUserDayServiceImpl(TimeBandAvailableUserDayRepository timeBandAvailableUserDayRepository, TimeBandAvailableUserDayMapper timeBandAvailableUserDayMapper) {
        this.timeBandAvailableUserDayRepository = timeBandAvailableUserDayRepository;
        this.timeBandAvailableUserDayMapper = timeBandAvailableUserDayMapper;
    }

    /**
     * Save a timeBandAvailableUserDay.
     *
     * @param timeBandAvailableUserDayDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TimeBandAvailableUserDayDTO save(TimeBandAvailableUserDayDTO timeBandAvailableUserDayDTO) {
        log.debug("Request to save TimeBandAvailableUserDay : {}", timeBandAvailableUserDayDTO);
        TimeBandAvailableUserDay timeBandAvailableUserDay = timeBandAvailableUserDayMapper.toEntity(timeBandAvailableUserDayDTO);
        timeBandAvailableUserDay = timeBandAvailableUserDayRepository.save(timeBandAvailableUserDay);
        return timeBandAvailableUserDayMapper.toDto(timeBandAvailableUserDay);
    }

    /**
     * Get all the timeBandAvailableUserDays.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TimeBandAvailableUserDayDTO> findAll() {
        log.debug("Request to get all TimeBandAvailableUserDays");
        return timeBandAvailableUserDayRepository.findAll().stream()
            .map(timeBandAvailableUserDayMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one timeBandAvailableUserDay by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TimeBandAvailableUserDayDTO> findOne(Long id) {
        log.debug("Request to get TimeBandAvailableUserDay : {}", id);
        return timeBandAvailableUserDayRepository.findById(id)
            .map(timeBandAvailableUserDayMapper::toDto);
    }

    /**
     * Delete the timeBandAvailableUserDay by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TimeBandAvailableUserDay : {}", id);
        timeBandAvailableUserDayRepository.deleteById(id);
    }
}
