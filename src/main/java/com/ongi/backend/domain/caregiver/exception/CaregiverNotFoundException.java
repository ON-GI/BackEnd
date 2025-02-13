package com.ongi.backend.domain.caregiver.exception;

import com.ongi.backend.common.exception.ApplicationException;

public class CaregiverNotFoundException extends ApplicationException {
    public CaregiverNotFoundException() {
        super(CaregiverErrorCase.CAREGIVER_NOT_FOUND);
    }
}
