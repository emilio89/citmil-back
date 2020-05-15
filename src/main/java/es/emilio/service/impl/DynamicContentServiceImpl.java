package es.emilio.service.impl;

import es.emilio.service.DynamicContentService;
import es.emilio.domain.DynamicContent;
import es.emilio.repository.DynamicContentRepository;
import es.emilio.service.dto.DynamicContentDTO;
import es.emilio.service.mapper.DynamicContentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DynamicContent}.
 */
@Service
@Transactional
public class DynamicContentServiceImpl implements DynamicContentService {

    private final Logger log = LoggerFactory.getLogger(DynamicContentServiceImpl.class);

    private final DynamicContentRepository dynamicContentRepository;

    private final DynamicContentMapper dynamicContentMapper;

    public DynamicContentServiceImpl(DynamicContentRepository dynamicContentRepository, DynamicContentMapper dynamicContentMapper) {
        this.dynamicContentRepository = dynamicContentRepository;
        this.dynamicContentMapper = dynamicContentMapper;
    }

    /**
     * Save a dynamicContent.
     *
     * @param dynamicContentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DynamicContentDTO save(DynamicContentDTO dynamicContentDTO) {
        log.debug("Request to save DynamicContent : {}", dynamicContentDTO);
        DynamicContent dynamicContent = dynamicContentMapper.toEntity(dynamicContentDTO);
        dynamicContent = dynamicContentRepository.save(dynamicContent);
        return dynamicContentMapper.toDto(dynamicContent);
    }

    /**
     * Get all the dynamicContents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DynamicContentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DynamicContents");
        return dynamicContentRepository.findAll(pageable)
            .map(dynamicContentMapper::toDto);
    }

    /**
     * Get one dynamicContent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DynamicContentDTO> findOne(Long id) {
        log.debug("Request to get DynamicContent : {}", id);
        return dynamicContentRepository.findById(id)
            .map(dynamicContentMapper::toDto);
    }

    /**
     * Delete the dynamicContent by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DynamicContent : {}", id);
        dynamicContentRepository.deleteById(id);
    }
}
