package org.jane.cns.spine.efferents.rest;

import java.io.IOException;

class RestEfferentCouldNotBeActivatedException extends RuntimeException {
    RestEfferentCouldNotBeActivatedException(IOException e) {
        super(e);
    }

    RestEfferentCouldNotBeActivatedException(String message) {
        super(message);
    }
}
