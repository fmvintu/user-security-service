package br.com.user.security.exception;

import static java.lang.String.format;

public class UserAlreadyExistsException extends BusinessException {

    public UserAlreadyExistsException(String externaCode, Long customerUid) {
        super(externaCode, customerUid, format("User exists with id: '%s'", customerUid));
    }

    public UserAlreadyExistsException(String externaCode, String info) {
        super(externaCode, info, format("User exists with: '%s'", info));
    }
}
