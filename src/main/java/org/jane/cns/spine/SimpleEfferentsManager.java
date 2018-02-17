package org.jane.cns.spine;

import org.jane.cns.spine.efferents.Efferent;
import org.jane.cns.spine.efferents.EfferentDescriptor;
import org.jane.cns.spine.efferents.EfferentFactory;
import org.jane.cns.spine.efferents.EfferentStatus;
import org.jane.cns.spine.efferents.store.EfferentsStore;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleEfferentsManager implements EfferentsManager {

    private final EfferentFactory efferentFactory;
    private final Map<String, Efferent> efferentsById;
    private final EfferentsStore store;

    public SimpleEfferentsManager(EfferentFactory efferentFactory, EfferentsStore store) {
        this.efferentFactory = efferentFactory;
        this.efferentsById = new HashMap<>();
        this.store = store;

        loadEfferents();
    }

    private void loadEfferents() {
        store.loadEfferentDescriptors().forEach(this::addEfferent);
    }

    @Override
    public Set<EfferentDescriptor> getEfferents() {
        return efferentsById.values().stream().map(Efferent::getEfferentDescriptor).collect(Collectors.toSet());
    }

    @Override
    public void addEfferent(EfferentDescriptor descriptor) {
        store.save(descriptor);
        efferentsById.put(descriptor.getId(), efferentFactory.createEfferent(descriptor));
    }

    @Override
    public void removeEfferent(String efferentId) {
        if (!efferentsById.containsKey(efferentId)) {
            throw new IllegalArgumentException("There is not an efferent with the id: " + efferentId);
        }
        store.remove(efferentId);
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
