package org.jane.cns.spine;

import org.jane.cns.spine.efferents.Efferent;
import org.jane.cns.spine.efferents.EfferentDescriptor;
import org.jane.cns.spine.efferents.EfferentFactory;
import org.jane.cns.spine.efferents.store.EfferentsStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Set;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class EfferentCreationAndRemovalTests {

    private SimpleEfferentsManager simpleEfferentManager;
    private EfferentFactory efferentFactory;

    @Before
    public void setUp() throws Exception {
        efferentFactory = Mockito.mock(EfferentFactory.class);
        EfferentsStore efferentsStore = Mockito.mock(EfferentsStore.class);
        simpleEfferentManager = new SimpleEfferentsManager(efferentFactory, efferentsStore);
    }

    @Test
    public void atFirstNoEfferentsExisting() {
        Assert.assertTrue(simpleEfferentManager.getEfferents().isEmpty());
    }

    @Test
    public void whenEfferentCreatedCanBeObtained() {
        EfferentDescriptor descriptor = Mockito.mock(EfferentDescriptor.class);
        addEfferentToManager(descriptor);

        Assert.assertEquals(Set.of(descriptor), simpleEfferentManager.getEfferents());
    }

    @Test
    public void whenRemovedTestDoesNotExist() {
        EfferentDescriptor descriptor = Mockito.mock(EfferentDescriptor.class);
        when(descriptor.getId()).thenReturn("firstEfferent");
        addEfferentToManager(descriptor);
        Assert.assertEquals(Set.of(descriptor), simpleEfferentManager.getEfferents());

        simpleEfferentManager.removeEfferent("firstEfferent");

        Assert.assertTrue(simpleEfferentManager.getEfferents().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotRemoveNonExistentEfferent() {
        simpleEfferentManager.removeEfferent("non existing efferent");
    }

    private void addEfferentToManager(EfferentDescriptor descriptor) {
        Efferent efferent = Mockito.mock(Efferent.class);
        when(efferentFactory.createEfferent(eq(descriptor))).thenReturn(efferent);
        when(efferent.getEfferentDescriptor()).thenReturn(descriptor);
        simpleEfferentManager.addEfferent(descriptor);
    }
}
