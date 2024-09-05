package me.albert.todo.service;

import lombok.RequiredArgsConstructor;
import me.albert.todo.domain.Tag;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.repository.TagRepository;
import me.albert.todo.service.dto.response.IdResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {

    public static final String TAG_ALREADY_EXISTS = "이미 존재하는 태그입니다.";

    private final TagRepository tagRepository;

    @Transactional
    @Override
    public IdResponse createTag(String name) {
        if (tagRepository.existsByName(name)) {
            throw new BusinessException(TAG_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
        Tag tag = new Tag(name);
        try {
            tagRepository.save(tag);
            return new IdResponse(tag.getId());
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(TAG_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
    }

}
