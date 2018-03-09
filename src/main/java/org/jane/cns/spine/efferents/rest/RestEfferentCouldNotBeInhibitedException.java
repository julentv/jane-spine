package org.jane.cns.spine.efferents.rest;

class RestEfferentCouldNotBeInhibitedException extends RuntimeException {

    RestEfferentCouldNotBeInhibitedException(Throwable throwable) {
        super(throwable);
    }

    RestEfferentCouldNotBeInhibitedException(String message) {
        super(message);
    }
}
