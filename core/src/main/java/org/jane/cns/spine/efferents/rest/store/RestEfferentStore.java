package org.jane.cns.spine.efferents.rest.store;

import org.jane.cns.spine.efferents.rest.RestEfferentDescriptor;

import java.util.Set;

public interface RestEfferentStore {

    Set<RestEfferentDescriptor> loadEfferentDescriptors();

    void save(RestEfferentDescriptor efferentDescriptor);

    void remove(String efferentId);
}
