package org.jane.cns.spine.efferents.rest;

public class RestEfferentFactory {
    public RestEfferent createEfferent(RestEfferentDescriptor efferentDescriptor) {
        return new RestEfferent(efferentDescriptor);
    }
}
