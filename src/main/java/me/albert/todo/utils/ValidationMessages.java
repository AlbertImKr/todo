package me.albert.todo.utils;

public class ValidationMessages {

    // Account
    public static final String ACCOUNT_USERNAME_NOT_NULL = "사용자 이름은 필수 입력값입니다.";
    public static final String ACCOUNT_USERNAME_MESSAGE = "사용자 이름은 5자 이상 20자 이하로 입력해야 합니다.";
    public static final String ACCOUNT_PASSWORD_NOT_NULL = "비밀번호는 필수 입력값입니다.";
    public static final String ACCOUNT_PASSWORD_MESSAGE = "비밀번호는 영문 대소문자, 숫자, 특수문자를 포함한 8자 이상 20자 이하로 입력해주세요.";
    public static final String ACCOUNT_CONFIRM_PASSWORD_NOT_NULL = "비밀번호 확인은 필수 입력값입니다.";
    public static final String ACCOUNT_CONFIRM_PASSWORD_MESSAGE = "비밀번호는 영문 대소문자, 숫자, 특수문자를 포함한 8자 이상 20자 이하로 입력해주세요.";

    // To-do
    public static final String TODO_TITLE_MESSAGE = "할 일 제목은 1자 이상 100자 이하로 입력해야 합니다.";
    public static final String TODO_TITLE_NOT_NULL = "할 일 제목은 필수 입력값입니다.";
    public static final String TODO_DESCRIPTION_NOT_NULL = "할 일 설명은 필수 입력값입니다.";
    public static final String TODO_DESCRIPTION_MESSAGE = "할 일 설명은 1000자를 넘을 수 없습니다.";
    public static final String TODO_DUE_DATE_NOT_NULL = "마감일은 필수 입력값입니다.";
    public static final String TODO_DUE_DATE_FUTURE = "과거 시간은 입력할 수 없습니다.";
    public static final String TODO_STATUS_NOT_NULL = "할 일 상태는 필수 입력값입니다.";

    // Project
    public static final String PROJECT_NAME_NOT_NULL = "프로젝트 이름은 필수 입력값입니다.";
    public static final String PROJECT_NAME_MESSAGE = "프로젝트 이름은 20자 이하로 입력해야 합니다.";
    public static final String PROJECT_ASSIGN_TODO_NOT_NULL = "할 일 ID는 필수 입력값입니다.";
    public static final String PROJECT_ID_NOT_NULL = "프로젝트 ID는 필수 입력값입니다.";
    public static final String PROJECT_ID_POSITIVE = "프로젝트 ID는 0보다 커야 합니다.";

    // Tag
    public static final String TAG_NAME_NOT_NULL = "태그 이름은 필수 입력값입니다.";
    public static final String TAG_NAME_MESSAGE = "태그 이름은 20자 이하로 입력해야 합니다.";
    public static final String TAG_ID_NOT_NULL = "태그 ID는 필수 입력값입니다.";
    public static final String TAG_ID_POSITIVE = "태그 ID는 0보다 커야 합니다.";

    // Recurring task
    public static final String RECURRING_TASK_RECURRENCE_PATTERN_MESSAGE = "반복 주기는 필수입니다.ISO8601 기간 형식으로 입력해주세요.";

    // To-do status
    public static final String TODO_STATUS_MESSAGE = "할 일 상태는 필수 입력값입니다.";

    // To-do priority
    public static final String TODO_PRIORITY_MESSAGE = "할 일 우선순위는 필수 입력값입니다.";

    // Notification setting
    public static final String NOTIFICATIONS_SETTING_NOT_NUll_MESSAGE = "알림 시간은 비어있을 수 없습니다.ISO-8601 형식으로 입력해주세요.";

    // group
    public static final String GROUP_NAME_NOT_NULL = "그룹 이름은 필수 입력값입니다.";
    public static final String GROUP_NAME_MESSAGE = "그룹 이름은 20자 이하로 입력해야 합니다.";
    public static final String GROUP_DESCRIPTION_NOT_NULL = "그룹 설명은 필수 입력값입니다.";
    public static final String GROUP_DESCRIPTION_MESSAGE = "그룹 설명은 100자 이하로 입력해야 합니다.";
    public static final String EMPTY_ACCOUNT_IDS = "계정 ID는 비어있을 수 없습니다.";

    private ValidationMessages() {
        throw new AssertionError("Cannot instantiate utility class");
    }
}
