package org.jane.cns.spine;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

public class SimpleEfferentsManagerTest {

    private SimpleEfferentsManager simpleEfferentManager;

    @Before
    public void setUp() throws Exception {
        simpleEfferentManager = new SimpleEfferentsManager();
    }

    @Test
    public void whenEfferentCreatedCanBeObtained() {
        EfferentDescriptor descriptor = new EfferentDescriptor("firstEfferent", "ip", "80", "first Efferent");
        simpleEfferentManager.addEfferents(descriptor);

        Assert.assertEquals(Set.of(descriptor), simpleEfferentManager.getEfferents());
    }

    @Test
    public void whenRemovedTestDoesNotExist() {
        EfferentDescriptor descriptor = new EfferentDescriptor("firstEfferent", "ip", "80", "first Efferent");
        simpleEfferentManager.addEfferents(descriptor);
        Set<EfferentDescriptor> efferents = simpleEfferentManager.getEfferents();
        Assert.assertEquals(Set.of(descriptor), efferents);

        simpleEfferentManager.removeEfferent(descriptor.getId());

        Assert.assertTrue(simpleEfferentManager.getEfferents().isEmpty());
    }

}
