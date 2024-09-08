package me.albert.todo.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import me.albert.todo.domain.Tag;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.repository.TagRepository;
import me.albert.todo.utils.ErrorMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

@DisplayName("태그 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagRepository tagRepository;

    @DisplayName("태그 이름으로 태그를 조회한다.")
    @Test
    void get_tag_by_name() {
        // given
        var name = "tag";
        var tag = mock(Tag.class);
        when(tagRepository.findByName(name)).thenReturn(java.util.Optional.of(tag));

        // when
        tagService.getTagByName(name);

        // then
        verify(tagRepository).findByName(name);
    }

    @DisplayName("태그 이름으로 조회했을 때 태그가 존재하지 않으면 예외가 발생한다.")
    @Test
    void get_tag_by_name_not_found() {
        // given
        var name = "tag";
        when(tagRepository.findByName(name)).thenReturn(java.util.Optional.empty());

        // when, then
        assertThatThrownBy(() -> tagService.getTagByName(name))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("message", ErrorMessages.TAG_NOT_FOUND)
                .hasFieldOrPropertyWithValue("httpStatusCode", HttpStatus.NOT_FOUND);
    }

    @DisplayName("태그를 생성한다.")
    @Test
    void create_tag() {
        // given
        var name = "tag";
        var tag = mock(Tag.class);
        when(tagRepository.existsByName(name)).thenReturn(false);
        when(tagRepository.save(any(Tag.class))).thenReturn(tag);

        // when
        tagService.createTag(name);

        // then
        verify(tagRepository).existsByName(name);
        verify(tagRepository).save(any(Tag.class));
    }

    @DisplayName("이미 존재하는 태그를 생성하려고 하면 예외가 발생한다.")
    @Test
    void create_tag_that_already_exists() {
        // given
        var name = "tag";
        when(tagRepository.existsByName(name)).thenReturn(true);

        // when, then
        assertThatThrownBy(() -> tagService.createTag(name))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("message", ErrorMessages.TAG_ALREADY_EXISTS)
                .hasFieldOrPropertyWithValue("httpStatusCode", HttpStatus.BAD_REQUEST);
    }

    @DisplayName("태그를 생성하다가 예외가 발생하면 예외가 발생한다.")
    @Test
    void create_tag_with_exception() {
        // given
        var name = "tag";
        when(tagRepository.existsByName(name)).thenReturn(false);
        when(tagRepository.save(any(Tag.class))).thenThrow(DataIntegrityViolationException.class);

        // when, then
        assertThatThrownBy(() -> tagService.createTag(name))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("message", ErrorMessages.TAG_ALREADY_EXISTS)
                .hasFieldOrPropertyWithValue("httpStatusCode", HttpStatus.BAD_REQUEST);
    }
}
