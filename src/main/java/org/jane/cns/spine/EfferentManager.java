package org.jane.cns.spine;

import java.util.List;

public interface EfferentManager {
    List<EfferentDescriptor> getEfferents();
    void addEfferents(EfferentDescriptor descriptor);
    void removeEfferent(String efferentId);
    void activateEfferent(String efferentId);
    void inhibitEfferent(String efferentId);
}
