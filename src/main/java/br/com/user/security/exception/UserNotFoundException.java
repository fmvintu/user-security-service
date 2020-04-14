package br.com.user.security.exception;

import static java.lang.String.format;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(String externaCode, Long userUid) {
        super(externaCode, userUid, format("User is not found with id: '%s'", userUid));
    }

    public UserNotFoundException(String externaCode, String userName) {
        super(externaCode, userName, format("User is not found: '%s'", userName));
    }

    public UserNotFoundException(String externaCode) {
        super(externaCode, "NO_USER_FOUND", "No user found");
    }
}
