package com.qa.bugkeeper.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BugKeeperException extends RuntimeException {

    public BugKeeperException(String message, Throwable cause) {
        super(message, cause);
        log.error("{}, error: {}", message, cause.getMessage());
    }
}
