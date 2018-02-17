package org.jane.cns.spine.efferents.store;

import org.jane.cns.spine.efferents.EfferentDescriptor;

import java.util.Set;

public interface EfferentsStore {
    Set<EfferentDescriptor> loadEfferentDescriptors();

    void save(EfferentDescriptor efferentDescriptor);

    void remove(String efferentId);
}
