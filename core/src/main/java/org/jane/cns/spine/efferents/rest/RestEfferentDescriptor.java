package org.jane.cns.spine.efferents.rest;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jane.cns.spine.efferents.EfferentDescriptor;

public class RestEfferentDescriptor extends EfferentDescriptor {

    private final String ip;
    private final Integer port;

    public RestEfferentDescriptor(String id, String ip, Integer port, String descriptor) {
        super(id, descriptor);
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public String getIpAndPort() {
        return ip + ":" + port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RestEfferentDescriptor that = (RestEfferentDescriptor) o;

        return new EqualsBuilder()
                .append(getId(), that.getId())
                .append(ip, that.ip)
                .append(port, that.port)
                .append(getDescriptor(), that.getDescriptor())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(ip)
                .append(port)
                .append(getDescriptor())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", getId())
                .append("descriptor", getDescriptor())
                .append("ip", ip)
                .append("port", port)
                .toString();
    }
}
