package org.jane.cns.spine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimpleEfferentManager implements EfferentManager {

    Map<String, EfferentDescriptor> efferentsById = new HashMap<>();

    @Override
    public Set<EfferentDescriptor> getEfferents() {
        return new HashSet<>(efferentsById.values());
    }

    @Override
    public void addEfferents(EfferentDescriptor descriptor) {
        efferentsById.put(descriptor.getId(), descriptor);
    }

    @Override
    public void removeEfferent(String efferentId) {
        efferentsById.remove(efferentId);
    }

    @Override
    public void activateEfferent(String efferentId) {

    }

    @Override
    public void inhibitEfferent(String efferentId) {

    }
}
