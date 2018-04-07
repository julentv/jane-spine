package org.jane.cns.spine;

import org.jane.cns.spine.efferents.Efferent;
import org.jane.cns.spine.efferents.EfferentDescriptor;
import org.jane.cns.spine.efferents.EfferentStatus;
import org.jane.cns.spine.efferents.rest.RestEfferentDescriptor;
import org.jane.cns.spine.efferents.rest.RestEfferentFactory;
import org.jane.cns.spine.efferents.rest.store.RestEfferentStore;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EfferentsManager {

    private final RestEfferentFactory efferentFactory;
    private final Map<String, Efferent> efferentsById;
    private final RestEfferentStore store;

    public EfferentsManager(RestEfferentFactory efferentFactory, RestEfferentStore store) {
        this.efferentFactory = efferentFactory;
        this.efferentsById = new HashMap<>();
        this.store = store;

        loadEfferents();
    }

    private void loadEfferents() {
        store.loadEfferentDescriptors().forEach(this::addRestEfferent);
    }

    public Set<EfferentDescriptor> getEfferents() {
        return efferentsById.values().stream().map(Efferent::getEfferentDescriptor).collect(Collectors.toSet());
    }

    public void addRestEfferent(RestEfferentDescriptor descriptor) {
        store.save(descriptor);
        efferentsById.put(descriptor.getId(), efferentFactory.createEfferent(descriptor));
    }

    public void removeEfferent(String efferentId) {
        if (!efferentsById.containsKey(efferentId)) {
            throw new IllegalArgumentException("There is not an efferent with the id: " + efferentId);
        }
        store.remove(efferentId);
        efferentsById.remove(efferentId);
    }

    public EfferentStatus getEfferentStatus(String efferentId) {
        return efferentsById.get(efferentId).getEfferentStatus();
    }

    public void activateEfferent(String efferentId) {
        efferentsById.get(efferentId).activate();
    }

    public void inhibitEfferent(String efferentId) {
        efferentsById.get(efferentId).inhibit();
    }
}
