package com.ongi.backend.common.error;

public interface ErrorCase  {
    Integer getHttpStatusCode();
    Integer getErrorCode();
    String getMessage();
}
