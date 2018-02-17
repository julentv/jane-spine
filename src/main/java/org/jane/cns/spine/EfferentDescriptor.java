package org.jane.cns.spine;

public class EfferentDescriptor {
    private final String id;
    private final String ip;
    private final String port;
    private final String descriptor;

    public EfferentDescriptor(String id, String ip, String port, String descriptor) {
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

    public String getPort() {
        return port;
    }

    public String getDescriptor() {
        return descriptor;
    }
}
