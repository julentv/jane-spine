package org.jane.cns.spine.efferents.rest;

import org.junit.Assert;
import org.junit.Test;

public class RestEfferentFactoryTest {
    @Test
    public void createEfferent() throws Exception {
        RestEfferentDescriptor efferentDescriptor = new RestEfferentDescriptor("desc1", "ip", 12, "desc1 descr");
        RestEfferent efferent = new RestEfferentFactory().createEfferent(efferentDescriptor);
        Assert.assertEquals(efferentDescriptor, efferent.getEfferentDescriptor());
    }
}
