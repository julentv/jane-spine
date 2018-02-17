package org.jane.cns.spine;

import java.util.Set;

public interface EfferentsManager {
    Set<EfferentDescriptor> getEfferents();

    void addEfferents(EfferentDescriptor descriptor);

    void removeEfferent(String efferentId);

    EfferentStatus getEfferentStatus(String efferentId);

    void activateEfferent(String efferentId);

    void inhibitEfferent(String efferentId);
}
