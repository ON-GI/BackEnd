package com.ongi.backend.domain.caregiver.exception;

import com.ongi.backend.common.exception.ApplicationException;

public class WorkConditionNotFoundException extends ApplicationException {
    public WorkConditionNotFoundException() {
        super(CaregiverErrorCase.WORK_CONDITION_NOT_FOUND);
    }
}
