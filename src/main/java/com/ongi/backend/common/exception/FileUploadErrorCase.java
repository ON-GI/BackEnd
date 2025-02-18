package com.ongi.backend.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileUploadErrorCase implements ErrorCase{
    FILE_UPLOAD_FAILED(1000, "파일 업로드에 실패했습니다."),
    FILE_DELETE_FAILED(1001, "파일 삭제에 실패했습니다.");

    private final Integer httpStatusCode = 400;
    private final Integer errorCode;
    private final String message;
}
