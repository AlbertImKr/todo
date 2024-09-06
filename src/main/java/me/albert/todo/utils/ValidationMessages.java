package me.albert.todo.utils;

public class ValidationMessages {
    public static final String TODO_TITLE_MESSAGE = "할 일 제목은 1자 이상 100자 이하로 입력해야 합니다.";
    public static final String TODO_TITLE_NOT_NULL = "할 일 제목은 필수 입력값입니다.";
    public static final String TODO_DESCRIPTION_NOT_NULL = "할 일 설명은 필수 입력값입니다.";
    public static final String TODO_DESCRIPTION_MESSAGE = "할 일 설명은 1000자를 넘을 수 없습니다.";
    public static final String TODO_DUE_DATE_NOT_NULL = "마감일은 필수 입력값입니다.";
    public static final String TODO_DUE_DATE_FUTURE = "과거 시간은 입력할 수 없습니다.";
}
