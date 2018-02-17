package org.jane.cns.spine.efferents;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class EfferentDescriptor {
    private final String id;
    private final String ip;
    private final Integer port;
    private final String descriptor;

    public EfferentDescriptor(String id, String ip, Integer port, String descriptor) {
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.descriptor = descriptor;
    }

    public String getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public String getDescriptor() {
        return descriptor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EfferentDescriptor that = (EfferentDescriptor) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(ip, that.ip)
                .append(port, that.port)
                .append(descriptor, that.descriptor)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(ip)
                .append(port)
                .append(descriptor)
                .toHashCode();
    }
}
