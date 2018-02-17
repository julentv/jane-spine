package org.jane.cns.spine.efferents;

public class SimpleEfferent implements Efferent {

    private final EfferentDescriptor efferentDescriptor;
    private final EfferentStatus status;

    public SimpleEfferent(EfferentDescriptor efferentDescriptor) {
        this.efferentDescriptor = efferentDescriptor;
        status = EfferentStatus.OFFLINE;
    }

    @Override
    public EfferentDescriptor getEfferentDescriptor() {
        return efferentDescriptor;
    }

    @Override
    public EfferentStatus getEfferentStatus() {
        return status;
    }

    @Override
    public void activate() {

    }

    @Override
    public void inhibit() {

    }
}
