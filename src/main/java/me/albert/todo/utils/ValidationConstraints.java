package me.albert.todo.utils;

public class ValidationConstraints {

    // account
    public static final String ACCOUNT_USERNAME_PATTERN = "^[a-zA-Z0-9]{5,20}$";
    public static final String ACCOUNT_PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).{8,20}$";

    // to-do
    public static final int TODO_TITLE_MIN_LENGTH = 1;
    public static final int TODO_TITLE_MAX_LENGTH = 100;
    public static final int TODO_DESCRIPTION_MAX_LENGTH = 1000;


    private ValidationConstraints() {
        throw new AssertionError("Cannot instantiate utility class");
    }
}
