package org.jane.cns.spine.service;

class ServiceException {
    private final String message;
    private final String trace;

    ServiceException(String message, String trace) {
        this.message = message;
        this.trace = trace;
    }

    public String getMessage() {
        return message;
    }

    public String getTrace() {
        return trace;
    }
}
