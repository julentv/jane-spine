package org.jane.cns.spine;

import java.util.Set;

public interface EfferentManager {
    Set<EfferentDescriptor> getEfferents();

    void addEfferents(EfferentDescriptor descriptor);

    void removeEfferent(String efferentId);

    void activateEfferent(String efferentId);

    void inhibitEfferent(String efferentId);
}
