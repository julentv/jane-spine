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
import static org.mockito.Mockito.when;

public class RestEfferentCreationAndRemovalTests {

    private EfferentsManager simpleEfferentManager;
    private EfferentFactory efferentFactory;

    @Before
    public void setUp() throws Exception {
        efferentFactory = Mockito.mock(EfferentFactory.class);
        RestEfferentStore efferentsStore = Mockito.mock(RestEfferentStore.class);
        simpleEfferentManager = new EfferentsManager(efferentFactory, efferentsStore);
    }

    @Test
    public void atFirstNoEfferentsExisting() {
        Assert.assertTrue(simpleEfferentManager.getEfferents().isEmpty());
    }

    @Test
    public void whenEfferentCreatedCanBeObtained() {
        RestEfferentDescriptor descriptor = Mockito.mock(RestEfferentDescriptor.class);
        addEfferentToManager(descriptor);

        Assert.assertEquals(Set.of(descriptor), simpleEfferentManager.getEfferents());
    }

    @Test
    public void whenRemovedTestDoesNotExist() {
        RestEfferentDescriptor descriptor = Mockito.mock(RestEfferentDescriptor.class);
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

    private void addEfferentToManager(RestEfferentDescriptor descriptor) {
        Efferent efferent = Mockito.mock(Efferent.class);
        when(efferentFactory.createEfferent(eq(descriptor))).thenReturn(efferent);
        when(efferent.getEfferentDescriptor()).thenReturn(descriptor);
        simpleEfferentManager.addRestEfferent(descriptor);
    }
}
