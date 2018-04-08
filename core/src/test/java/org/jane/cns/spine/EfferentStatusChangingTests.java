package org.jane.cns.spine;

import org.jane.cns.spine.efferents.EfferentStatus;
import org.jane.cns.spine.efferents.rest.RestEfferent;
import org.jane.cns.spine.efferents.rest.RestEfferentDescriptor;
import org.jane.cns.spine.efferents.rest.RestEfferentFactory;
import org.jane.cns.spine.efferents.rest.store.RestEfferentStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EfferentStatusChangingTests {

    private EfferentsManager simpleEfferentManager;
    private RestEfferentFactory efferentFactory;

    @Before
    public void setUp() throws Exception {
        efferentFactory = Mockito.mock(RestEfferentFactory.class);
        RestEfferentStore efferentsStore = Mockito.mock(RestEfferentStore.class);
        simpleEfferentManager = new EfferentsManager(efferentFactory, efferentsStore);
    }

    @Test
    public void canObtainTheEfferentStatus() {
        RestEfferent efferent = Mockito.mock(RestEfferent.class);
        addEfferentToManager(efferent, "firstEfferent");

        Assert.assertEquals(EfferentStatus.OFFLINE, simpleEfferentManager.getEfferentStatus("firstEfferent").get());
    }

    @Test
    public void noStatusIfNoEfferent() {
        RestEfferent efferent = Mockito.mock(RestEfferent.class);

        Assert.assertFalse(simpleEfferentManager.getEfferentStatus("firstEfferent").isPresent());
    }

    @Test
    public void canActivateEfferent() {
        RestEfferent efferent = Mockito.mock(RestEfferent.class);
        addEfferentToManager(efferent, "firstEfferent");

        simpleEfferentManager.activateEfferent("firstEfferent");

        verify(efferent).activate();
    }

    @Test
    public void canInhibitEfferent() {
        RestEfferent efferent = Mockito.mock(RestEfferent.class);
        addEfferentToManager(efferent, "firstEfferent");

        simpleEfferentManager.inhibitEfferent("firstEfferent");

        verify(efferent).inhibit();
    }

    private void addEfferentToManager(RestEfferent efferent, String id) {
        RestEfferentDescriptor descriptor = Mockito.mock(RestEfferentDescriptor.class);
        when(descriptor.getId()).thenReturn(id);
        when(efferentFactory.createEfferent(eq(descriptor))).thenReturn(efferent);
        when(efferent.getEfferentStatus()).thenReturn(EfferentStatus.OFFLINE);
        simpleEfferentManager.addRestEfferent(descriptor);
    }
}
