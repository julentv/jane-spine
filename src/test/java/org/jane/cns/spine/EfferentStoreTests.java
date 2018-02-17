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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EfferentStoreTests {

    private SimpleEfferentsManager efferentsManager;
    private EfferentsStore efferentsStore;
    private EfferentFactory efferentFactory;

    @Before
    public void setUp() throws Exception {
        efferentFactory = Mockito.mock(EfferentFactory.class);
        efferentsStore = Mockito.mock(EfferentsStore.class);
        efferentsManager = new SimpleEfferentsManager(efferentFactory, efferentsStore);
    }

    @Test
    public void storedEfferentsLoadedInTheBeginning() {
        EfferentDescriptor descriptor = new EfferentDescriptor("id", "ip", 80, "descriptor");
        Set<EfferentDescriptor> efferentDescriptors = Set.of(descriptor);
        Efferent efferent = Mockito.mock(Efferent.class);
        when(efferent.getEfferentDescriptor()).thenReturn(descriptor);
        when(efferentFactory.createEfferent(eq(descriptor))).thenReturn(efferent);
        when(efferentsStore.loadEfferentDescriptors()).thenReturn(efferentDescriptors);
        efferentsManager = new SimpleEfferentsManager(efferentFactory, efferentsStore);

        Assert.assertEquals(efferentDescriptors, efferentsManager.getEfferents());
    }

    @Test
    public void efferentStoredWhenCreated() {
        EfferentDescriptor descriptor = new EfferentDescriptor("id", "ip", 80, "descriptor");
        efferentsManager.addEfferent(descriptor);
        verify(efferentsStore).save(eq(descriptor));
    }

    @Test
    public void efferentRemovedFromStoreWhenRemoval() {
        EfferentDescriptor descriptor = new EfferentDescriptor("id", "ip", 80, "descriptor");
        efferentsManager.addEfferent(descriptor);

        efferentsManager.removeEfferent("id");

        verify(efferentsStore).remove(eq("id"));
    }
}
