package org.jane.cns.spine;

import org.jane.cns.spine.efferents.Efferent;
import org.jane.cns.spine.efferents.EfferentDescriptor;
import org.jane.cns.spine.efferents.EfferentFactory;
import org.jane.cns.spine.efferents.EfferentStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EfferentStatusChanging {

    private SimpleEfferentsManager simpleEfferentManager;
    private EfferentFactory efferentFactory;

    @Before
    public void setUp() throws Exception {
        efferentFactory = Mockito.mock(EfferentFactory.class);
        simpleEfferentManager = new SimpleEfferentsManager(efferentFactory);
    }

    @Test
    public void canObtainTheEfferentStatus() {
        Efferent efferent = Mockito.mock(Efferent.class);
        addEfferentToManager(efferent, "firstEfferent");

        Assert.assertEquals(EfferentStatus.OFFLINE, simpleEfferentManager.getEfferentStatus("firstEfferent"));
    }

    @Test
    public void canActivateEfferent() {
        Efferent efferent = Mockito.mock(Efferent.class);
        addEfferentToManager(efferent, "firstEfferent");

        simpleEfferentManager.activateEfferent("firstEfferent");

        verify(efferent).activate();
    }

    @Test
    public void canInhibitEfferent() {
        Efferent efferent = Mockito.mock(Efferent.class);
        addEfferentToManager(efferent, "firstEfferent");

        simpleEfferentManager.inhibitEfferent("firstEfferent");

        verify(efferent).inhibit();
    }

    private void addEfferentToManager(Efferent efferent, String id) {
        EfferentDescriptor descriptor = new EfferentDescriptor(id, "ip", "80", "first Efferent");
        when(efferentFactory.createEfferent(eq(descriptor))).thenReturn(efferent);
        when(efferent.getEfferentStatus()).thenReturn(EfferentStatus.OFFLINE);
        simpleEfferentManager.addEfferents(descriptor);
    }
}
