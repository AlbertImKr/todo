package me.albert.todo.service;

import lombok.RequiredArgsConstructor;
import me.albert.todo.domain.Tag;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.repository.TagRepository;
import me.albert.todo.service.dto.response.IdResponse;
import me.albert.todo.utils.ErrorMessages;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Transactional
    @Override
    public IdResponse createTag(String name) {
        if (tagRepository.existsByName(name)) {
            throw new BusinessException(ErrorMessages.TAG_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
        Tag tag = new Tag(name);
        try {
            tagRepository.save(tag);
            return new IdResponse(tag.getId());
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(ErrorMessages.TAG_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public IdResponse getTagByName(String name) {
        Tag tag = tagRepository.findByName(name)
                .orElseThrow(() -> new BusinessException(ErrorMessages.TAG_NOT_FOUND, HttpStatus.NOT_FOUND));
        return new IdResponse(tag.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public Tag findById(Long tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new BusinessException(ErrorMessages.TAG_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override
    public Tag findByName(String tagName) {
        return tagRepository.findByName(tagName)
                .orElseThrow(() -> new BusinessException(ErrorMessages.TAG_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
