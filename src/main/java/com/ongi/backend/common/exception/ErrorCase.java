package com.ongi.backend.common.exception;

public interface ErrorCase  {
    Integer getHttpStatusCode();
    Integer getErrorCode();
    String getMessage();
}
