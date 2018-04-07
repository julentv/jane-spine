package org.jane.cns.spine;

import org.jane.cns.spine.efferents.Efferent;
import org.jane.cns.spine.efferents.EfferentFactory;
import org.jane.cns.spine.efferents.rest.RestEfferentDescriptor;
import org.jane.cns.spine.efferents.rest.store.RestEfferentStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Set;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EfferentStoreTests {

    private EfferentsManager efferentsManager;
    private RestEfferentStore efferentsStore;
    private EfferentFactory efferentFactory;

    @Before
    public void setUp() throws Exception {
        efferentFactory = Mockito.mock(EfferentFactory.class);
        efferentsStore = Mockito.mock(RestEfferentStore.class);
        efferentsManager = new EfferentsManager(efferentFactory, efferentsStore);
    }

    @Test
    public void storedEfferentsLoadedInTheBeginning() {
        RestEfferentDescriptor descriptor = Mockito.mock(RestEfferentDescriptor.class);
        Set<RestEfferentDescriptor> efferentDescriptors = Set.of(descriptor);
        Efferent efferent = Mockito.mock(Efferent.class);
        when(efferent.getEfferentDescriptor()).thenReturn(descriptor);
        when(efferentFactory.createEfferent(eq(descriptor))).thenReturn(efferent);
        when(efferentsStore.loadEfferentDescriptors()).thenReturn(efferentDescriptors);
        efferentsManager = new EfferentsManager(efferentFactory, efferentsStore);

        Assert.assertEquals(efferentDescriptors, efferentsManager.getEfferents());
    }

    @Test
    public void efferentStoredWhenCreated() {
        RestEfferentDescriptor descriptor = Mockito.mock(RestEfferentDescriptor.class);
        efferentsManager.addRestEfferent(descriptor);
        verify(efferentsStore).save(eq(descriptor));
    }

    @Test
    public void efferentRemovedFromStoreWhenRemoval() {
        RestEfferentDescriptor descriptor = Mockito.mock(RestEfferentDescriptor.class);
        when(descriptor.getId()).thenReturn("id");
        efferentsManager.addRestEfferent(descriptor);

        efferentsManager.removeEfferent("id");

        verify(efferentsStore).remove(eq("id"));
    }
}
