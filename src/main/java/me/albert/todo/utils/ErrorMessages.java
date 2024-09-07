package me.albert.todo.utils;

public class ErrorMessages {

    // Account
    public static final String PASSWORD_NOT_MATCHED = "비밀번호가 일치하지 않습니다.";
    public static final String USERNAME_IS_EXISTED = "이미 존재하는 유저 이름입니다.";
    public static final String USERNAME_OR_PASSWORD_NOT_MATCHED = "유저 이름 또는 비밀번호가 일치하지 않습니다.";
    public static final String USERNAME_NOT_EXISTED = "존재하지 않는 유저 이름입니다.";

    // To-do
    public static final String TODO_NOT_FOUND = "할 일을 찾을 수 없습니다.";
    public static final String TODO_UPDATE_NOT_ALLOWED = "할 일을 수정할 권한이 없습니다.";
    public static final String TODO_DELETE_NOT_ALLOWED = "할 일을 삭제할 권한이 없습니다.";

    // Project
    public static final String PROJECT_NOT_FOUND = "프로젝트를 찾을 수 없습니다.";
    public static final String PROJECT_UPDATE_NOT_ALLOWED = "프로젝트를 수정할 권한이 없습니다.";
    public static final String PROJECT_DELETE_NOT_ALLOWED = "프로젝트를 삭제할 권한이 없습니다.";

    private ErrorMessages() {
        throw new AssertionError("Cannot instantiate utility class");
    }
}
