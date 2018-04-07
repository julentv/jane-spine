package org.jane.cns.spine.efferents;

public class EfferentDescriptor {
    private final String id;
    private final String descriptor;

    public EfferentDescriptor(String id, String descriptor) {
        this.id = id;
        this.descriptor = descriptor;
    }

    public String getId() {
        return id;
    }

    public String getDescriptor() {
        return descriptor;
    }
}
