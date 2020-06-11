package es.emilio.service.impl;

import es.emilio.service.CalendarYearUserService;
import es.emilio.domain.CalendarYearUser;
import es.emilio.repository.CalendarYearUserRepository;
import es.emilio.service.dto.CalendarYearUserDTO;
import es.emilio.service.dto.EventCalendarProfesionalDTO;
import es.emilio.service.mapper.CalendarYearUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CalendarYearUser}.
 */
@Service
@Transactional
public class CalendarYearUserServiceImpl implements CalendarYearUserService {

    private final Logger log = LoggerFactory.getLogger(CalendarYearUserServiceImpl.class);

    private final CalendarYearUserRepository calendarYearUserRepository;

    private final CalendarYearUserMapper calendarYearUserMapper;

    public CalendarYearUserServiceImpl(CalendarYearUserRepository calendarYearUserRepository, CalendarYearUserMapper calendarYearUserMapper) {
        this.calendarYearUserRepository = calendarYearUserRepository;
        this.calendarYearUserMapper = calendarYearUserMapper;
    }

    /**
     * Save a calendarYearUser.
     *
     * @param calendarYearUserDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CalendarYearUserDTO save(CalendarYearUserDTO calendarYearUserDTO) {
        log.debug("Request to save CalendarYearUser : {}", calendarYearUserDTO);
        CalendarYearUser calendarYearUser = calendarYearUserMapper.toEntity(calendarYearUserDTO);
        calendarYearUser = calendarYearUserRepository.save(calendarYearUser);
        return calendarYearUserMapper.toDto(calendarYearUser);
    }

    /**
     * Get all the calendarYearUsers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CalendarYearUserDTO> findAll() {
        log.debug("Request to get all CalendarYearUsers");
        return calendarYearUserRepository.findAll().stream()
            .map(calendarYearUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one calendarYearUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CalendarYearUserDTO> findOne(Long id) {
        log.debug("Request to get CalendarYearUser : {}", id);
        return calendarYearUserRepository.findById(id)
            .map(calendarYearUserMapper::toDto);
    }

    /**
     * Delete the calendarYearUser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CalendarYearUser : {}", id);
        calendarYearUserRepository.deleteById(id);
    }

	@Override
	public List<EventCalendarProfesionalDTO> getAllEventCalendarProfesional(Long companyId) {
		// TODO Auto-generated method stub
		return calendarYearUserRepository.findEventCalendarProfesional(companyId);
	}
}
