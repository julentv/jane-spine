package org.jane.cns.spine;

import org.jane.cns.spine.efferents.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleEfferentsManager implements EfferentsManager {

    private final EfferentFactory efferentFactory;
    private final Map<String, Efferent> efferentsById = new HashMap<>();

    public SimpleEfferentsManager(EfferentFactory efferentFactory) {
        this.efferentFactory = efferentFactory;
    }

    @Override
    public Set<EfferentDescriptor> getEfferents() {
        return efferentsById.values().stream().map(Efferent::getEfferentDescriptor).collect(Collectors.toSet());
    }

    @Override
    public void addEfferents(EfferentDescriptor descriptor) {
        efferentsById.put(descriptor.getId(), efferentFactory.createEfferent(descriptor));
    }

    @Override
    public void removeEfferent(String efferentId) {
        if (!efferentsById.containsKey(efferentId)) {
            throw new IllegalArgumentException("There is not an efferent with the id: " + efferentId);
        }
        efferentsById.remove(efferentId);
    }

    @Override
    public EfferentStatus getEfferentStatus(String efferentId) {
        return efferentsById.get(efferentId).getEfferentStatus();
    }

    @Override
    public void activateEfferent(String efferentId) {
        efferentsById.get(efferentId).activate();
    }

    @Override
    public void inhibitEfferent(String efferentId) {
        efferentsById.get(efferentId).inhibit();
    }
}
