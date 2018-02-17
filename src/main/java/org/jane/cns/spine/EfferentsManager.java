package org.jane.cns.spine;

import org.jane.cns.spine.efferents.EfferentDescriptor;
import org.jane.cns.spine.efferents.EfferentStatus;

import java.util.Set;

public interface EfferentsManager {
    Set<EfferentDescriptor> getEfferents();

    void addEfferent(EfferentDescriptor descriptor);

    void removeEfferent(String efferentId);

    EfferentStatus getEfferentStatus(String efferentId);

    void activateEfferent(String efferentId);

    void inhibitEfferent(String efferentId);
}
