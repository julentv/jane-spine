package org.jane.cns.spine.efferents;

public interface Efferent {

    EfferentDescriptor getEfferentDescriptor();

    EfferentStatus getEfferentStatus();

    void activate();

    void inhibit();
}
