package me.albert.todo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.utils.ErrorMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("할 일 관련 도메인 테스트")
class TodoTest {

    Todo todo;
    Account account;

    @BeforeEach
    void setUp() {
        account = new Account(1L);
        todo = new Todo("title", "description", LocalDateTime.now(), account, LocalDateTime.now(),
                        LocalDateTime.now(), TodoStatus.PENDING, TodoPriority.MEDIUM
        );
    }

    @DisplayName("그룹 할일의 태그를 해제한다.")
    @Test
    void unassign_tag_for_group() {
        // given
        var tag = new Tag(1L, "tag");
        var groupId = 1L;
        todo.assignGroup(new Group(groupId));
        todo.assignTag(tag);

        // when
        todo.unassignTag(tag);

        // then
        assertThat(todo.containsTag(tag)).isFalse();
    }

    @DisplayName("그룹 할일에 태그를 할당한다.")
    @Test
    void assign_tag_for_group() {
        // given
        var tag = new Tag(1L, "tag");
        var groupId = 1L;
        todo.assignGroup(new Group(groupId));

        // when
        todo.assignTag(tag);

        // then
        assertThat(todo.containsTag(tag)).isTrue();
    }

    @DisplayName("그룹 할일의 상태를 변경한다.")
    @Test
    void update_status() {
        // given
        var status = TodoStatus.COMPLETED;
        var updatedAt = LocalDateTime.now();
        var groupId = 1L;
        todo.assignGroup(new Group(groupId));

        // when
        todo.updateStatus(status, updatedAt,groupId);

        // then
        Assertions.assertAll(
                () -> assertThat(todo.getStatus()).isEqualTo(status),
                () -> assertThat(todo.getUpdatedAt()).isEqualTo(updatedAt)
        );
    }

    @DisplayName("그룹 아이디를 검증한다")
    @Test
    void validate_group_id() {
        // given
        var groupId = 1L;
        todo.assignGroup(new Group(groupId));

        // when, then
        todo.validateGroupId(groupId);
    }

    @DisplayName("그룹 아이디가 일치하지 않으면 예외가 발생한다")
    @Test
    void validate_group_id_fail() {
        // given
        var groupId = 1L;
        todo.assignGroup(new Group(2L));

        // when, then
        assertThatThrownBy(() -> todo.validateGroupId(groupId))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.TODO_NOT_IN_GROUP);
    }

    @DisplayName("할일의 상태를 변경한다. (그룹) ")
    @Test
    void update_status_for_group() {
        // given
        var status = TodoStatus.COMPLETED;
        var updatedAt = LocalDateTime.now();
        var groupId = 1L;
        todo.assignGroup(new Group(groupId));

        // when
        todo.updateStatus(status, updatedAt, groupId);

        // then
        Assertions.assertAll(
                () -> assertThat(todo.getStatus()).isEqualTo(status),
                () -> assertThat(todo.getUpdatedAt()).isEqualTo(updatedAt)
        );
    }

    @DisplayName("할일의 상태를 변경할 때 그룹아이디와 일치하지 않으면 실패한다. (그룹)")
    @Test
    void update_status_for_group_fail() {
        // given
        var status = TodoStatus.COMPLETED;
        var updatedAt = LocalDateTime.now();
        var groupId = 1L;
        todo.assignGroup(new Group(2L));

        // when
        var exception = Assertions.assertThrows(BusinessException.class, () -> {
            todo.updateStatus(status, updatedAt, groupId);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorMessages.TODO_NOT_IN_GROUP);
    }

    @DisplayName("할일의 상태를 변경할 때 그룹이 없으면 실패한다. (그룹)")
    @Test
    void update_status_for_group_fail_without_group() {
        // given
        var status = TodoStatus.COMPLETED;
        var updatedAt = LocalDateTime.now();
        var groupId = 1L;

        // when,then
        assertThatThrownBy(() -> todo.updateStatus(status, updatedAt, groupId))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.TODO_NOT_IN_GROUP);
    }

    @DisplayName("할일을 업데이트한다. (그룹)")
    @Test
    void update_todo_for_group() {
        // given
        var title = "updated title";
        var description = "updated description";
        var dueDate = LocalDateTime.now();
        var status = TodoStatus.COMPLETED;
        var updatedAt = LocalDateTime.now();
        var groupId = 1L;
        todo.assignGroup(new Group(groupId));

        // when
        todo.update(title, description, dueDate, updatedAt, status, groupId);

        // then
        Assertions.assertAll(
                () -> assertThat(todo.getTitle()).isEqualTo(title),
                () -> assertThat(todo.getDescription()).isEqualTo(description),
                () -> assertThat(todo.getDueDate()).isEqualTo(dueDate),
                () -> assertThat(todo.getStatus()).isEqualTo(status),
                () -> assertThat(todo.getUpdatedAt()).isEqualTo(updatedAt)
        );
    }

    @DisplayName("할일의 그룹아이디와 일치하지 않으면 업데이트 실패한다 (그룹)")
    @Test
    void update_todo_for_group_fail() {
        // given
        var title = "updated title";
        var description = "updated description";
        var dueDate = LocalDateTime.now();
        var status = TodoStatus.COMPLETED;
        var updatedAt = LocalDateTime.now();
        var groupId = 1L;
        todo.assignGroup(new Group(2L));

        // when
        var exception = Assertions.assertThrows(BusinessException.class, () -> {
            todo.update(title, description, dueDate, updatedAt, status, groupId);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorMessages.TODO_NOT_IN_GROUP);
    }


    @DisplayName("할일에 그룹을 할당한다.")
    @Test
    void assign_group() {
        // given
        var group = new Group(1L);

        // when
        todo.assignGroup(group);

        // then
        assertThat(todo.getGroup()).isEqualTo(group);
    }

    @DisplayName("할 일의 알림을 삭제 한다")
    @Test
    void delete_notifications() {
        // given
        var duration = Duration.ofHours(1);
        todo.updateNotificationSettings(List.of(duration), account);

        // when
        todo.deleteNotificationSettings(account);

        // then
        assertThat(todo.getNotificationSettings()).isEmpty();
    }

    @DisplayName("할 일의 알림을 업데이트 한다.")
    @Test
    void update_notifications() {
        // given
        var firstDuration = Duration.ofHours(1);
        var secondDuration = Duration.ofHours(2);
        var durations = List.of(firstDuration, secondDuration);

        // when
        todo.updateNotificationSettings(durations, account);

        // then
        var notifications = todo.getNotificationSettings();
        Assertions.assertAll(
                () -> assertThat(notifications).hasSize(2),
                () -> assertThat(notifications).contains(
                        firstDuration, secondDuration
                )
        );
    }

    @DisplayName("할 일의 알림을 업데이트할 때 기존 알림이 삭제된다.")
    @Test
    void update_notifications_delete_old_notifications() {
        // given
        var duration = Duration.ofHours(1);
        todo.updateNotificationSettings(List.of(duration), account);
        var newDuration = Duration.ofHours(2);

        // when
        todo.updateNotificationSettings(List.of(newDuration), account);

        // then
        var notification = todo.getNotificationSettings().get(0);
        Assertions.assertAll(
                () -> assertThat(todo.getNotificationSettings()).hasSize(1),
                () -> assertThat(notification).isEqualTo(newDuration)
        );
    }


    @DisplayName("할 일의 우선 순위를 변경한다.")
    @Test
    void update_priority() {
        // given
        var priority = TodoPriority.HIGH;

        // when
        todo.updatePriority(priority, account);

        // then
        assertThat(todo.getPriority()).isEqualTo(priority);
    }

    @DisplayName("할 일의 우선 순위를 변경할 때 권한이 없으면 예외가 발생한다.")
    @Test
    void update_priority_without_permission() {
        // given
        var priority = TodoPriority.HIGH;
        var anotherAccount = new Account(2L);

        // when
        var exception = Assertions.assertThrows(BusinessException.class, () -> {
            todo.updatePriority(priority, anotherAccount);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorMessages.TODO_UPDATE_NOT_ALLOWED);
    }

    @DisplayName("할 일에 반복 작업을 추가한다.")
    @Test
    void add_recurring_task() {
        // given
        var recurrencePattern = Period.ofDays(1);
        var nextOccurrence = LocalDateTime.now().plusDays(1);
        var recurringTask = new RecurringTask(recurrencePattern, nextOccurrence);

        // when
        todo.updateRecurringTask(recurringTask);

        // then
        assertThat(todo.getRecurringTask()).isEqualTo(recurringTask);
    }

    @DisplayName("할일에 태그를 해제한다.")
    @Test
    void unassign_tag() {
        // given
        var tag = new Tag(1L, "tag");
        todo.assignTag(tag);

        // when
        todo.unassignTag(tag);

        // then
        assertThat(todo.containsTag(tag)).isFalse();
    }

    @DisplayName("할일에 태그를 할당한다.")
    @Test
    void assign_tag() {
        // given
        var tag = new Tag(1L, "tag");

        // when
        todo.assignTag(tag);

        // then
        assertThat(todo.containsTag(tag)).isTrue();
    }

    @DisplayName("할일에 프로젝트 할당을 해제한다.")
    @Test
    void unassign_project() {
        // given
        var project = new Project(1L);
        todo.assignToProject(project);

        // when
        todo.unassignFromProject();

        // then
        assertThat(todo.getProject()).isNull();
    }

    @DisplayName("할일에 프로젝트를 할당한다.")
    @Test
    void assign_project() {
        // given
        var project = new Project(1L);

        // when
        todo.assignToProject(project);

        // then
        assertThat(todo.getProject()).isEqualTo(project);
    }

    @DisplayName("할일 수정이 정상적으로 이루어진다.")
    @Test
    void update_todo() {
        // given
        var title = "updated title";
        var description = "updated description";
        var dueDate = LocalDateTime.now();
        var status = TodoStatus.COMPLETED;
        var updatedAt = LocalDateTime.now();

        // when
        todo.update(title, description, dueDate, updatedAt, status, account);

        // then
        Assertions.assertAll(
                () -> assertThat(todo.getTitle()).isEqualTo(title),
                () -> assertThat(todo.getDescription()).isEqualTo(description),
                () -> assertThat(todo.getDueDate()).isEqualTo(dueDate),
                () -> assertThat(todo.getStatus()).isEqualTo(status),
                () -> assertThat(todo.getUpdatedAt()).isEqualTo(updatedAt)
        );
    }

    @DisplayName("할일 수정 시 권한이 없으면 예외가 발생한다.")
    @Test
    void update_todo_without_permission() {
        // given
        var title = "updated title";
        var description = "updated description";
        var dueDate = LocalDateTime.now();
        var status = TodoStatus.COMPLETED;
        var updatedAt = LocalDateTime.now();
        var anotherAccount = new Account(2L);

        // when
        var exception = Assertions.assertThrows(BusinessException.class, () -> {
            todo.update(title, description, dueDate, updatedAt, status, anotherAccount);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorMessages.TODO_UPDATE_NOT_ALLOWED);
    }

    @DisplayName("할일 수정 시 상태가 변경되면 상태 변경일이 업데이트된다.")
    @Test
    void update_todo_status() {
        // given
        var status = TodoStatus.COMPLETED;
        var updatedAt = LocalDateTime.now();

        // when
        todo.updateStatus(status, updatedAt, account);

        // then
        Assertions.assertAll(
                () -> assertThat(todo.getStatus()).isEqualTo(status),
                () -> assertThat(todo.getUpdatedAt()).isEqualTo(updatedAt)
        );
    }

    @DisplayName("할일 수정 시 상태 변경 시 권한이 없으면 예외가 발생한다.")
    @Test
    void update_todo_status_without_permission() {
        // given
        var status = TodoStatus.COMPLETED;
        var updatedAt = LocalDateTime.now();
        var anotherAccount = new Account(2L);

        // when
        var exception = Assertions.assertThrows(BusinessException.class, () -> {
            todo.updateStatus(status, updatedAt, anotherAccount);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorMessages.TODO_UPDATE_NOT_ALLOWED);
    }

    @DisplayName("할일에 사용자를 할당한다.")
    @Test
    void assign_user() {
        // given
        var assignee = new Account();

        // when
        todo.assignUser(assignee);

        // then
        assertThat(todo.containsAssignee(assignee)).isTrue();
    }

    @DisplayName("할일에 사용자를 중복해서 할당하면 중복으로 할당되지 않는다.")
    @Test
    void assign_user_duplicate() {
        // given
        var assignee = new Account();
        todo.assignUser(assignee);

        // when
        todo.assignUser(assignee);

        // then
        assertThat(todo.getAssignees()).hasSize(1);
    }

    @DisplayName("할일에 여러 사용자를 할당한다.")
    @Test
    void assign_multiple_users() {
        // given
        var assignee1 = new Account(1L);
        var assignee2 = new Account(2L);

        // when
        todo.assignUser(assignee1);
        todo.assignUser(assignee2);

        // then
        assertThat(todo.getAssignees()).hasSize(2);
    }

    @DisplayName("할일의 ID가 같으면 같은 할일로 판단한다.")
    @Test
    void equals_todo() {
        // given
        var todo1 = new Todo(1L);
        var todo2 = new Todo(1L);

        // then
        assertThat(todo1).isEqualTo(todo2);
    }

    @DisplayName("할일의 ID가 다르면 다른 할일로 판단한다.")
    @Test
    void not_equals_todo() {
        // given
        var todo1 = new Todo(1L);
        var todo2 = new Todo(2L);

        // then
        assertThat(todo1).isNotEqualTo(todo2);
    }

    @DisplayName("할일에서 사용자를 해제한다.")
    @Test
    void unassign_user() {
        // given
        var assignee = new Account();
        todo.assignUser(assignee);

        // when
        todo.unassignUser(assignee);

        // then
        assertThat(todo.getAssignees()).isEmpty();
    }

    @DisplayName("할일의 소유자이면 true를 반환한다.")
    @Test
    void is_owner() {
        // given
        var owner = new Account(1L);
        todo = new Todo(
                "title", "description", LocalDateTime.now(), owner, LocalDateTime.now(),
                LocalDateTime.now(), TodoStatus.PENDING, TodoPriority.MEDIUM
        );

        // then
        assertThat(todo.isOwner(owner)).isTrue();
    }

    @DisplayName("할일의 소유자가 아니면 false를 반환한다.")
    @Test
    void is_not_owner() {
        // given
        var owner = new Account(1L);
        var anotherAccount = new Account(2L);
        todo = new Todo(
                "title", "description", LocalDateTime.now(), owner, LocalDateTime.now(),
                LocalDateTime.now(), TodoStatus.PENDING, TodoPriority.MEDIUM
        );

        // then
        assertThat(todo.isOwner(anotherAccount)).isFalse();
    }
}
