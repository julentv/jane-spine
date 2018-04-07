package org.jane.cns.spine.efferents.rest.store;

import org.jane.cns.spine.efferents.rest.RestEfferentDescriptor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.util.Iterator;
import java.util.Set;

public class FileRestEfferentStoreTest {

    private FileRestEfferentStore fileRestEfferentStore;

    @Before
    public void setUp() throws Exception {
        fileRestEfferentStore = new FileRestEfferentStore();
    }

    @After
    public void tearDown() throws Exception {
        Files.delete(fileRestEfferentStore.getFile());
    }

    @Test
    public void saveAndLoad() throws Exception {
        fileRestEfferentStore.save(new RestEfferentDescriptor("restEfferent1", "ip1", 12, "restEfferent desc"));
        fileRestEfferentStore.save(new RestEfferentDescriptor("restEfferent2", "ip2", 24, "restEfferent2 desc"));
        Set<RestEfferentDescriptor> efferents = fileRestEfferentStore.loadEfferentDescriptors();
        Assert.assertEquals(2, efferents.size());

        Iterator<RestEfferentDescriptor> iterator = efferents.iterator();
        RestEfferentDescriptor descriptor1 = iterator.next();
        Assert.assertEquals("restEfferent1", descriptor1.getId());
        Assert.assertEquals("ip1", descriptor1.getIp());
        Assert.assertEquals(12, descriptor1.getPort().intValue());
        Assert.assertEquals("restEfferent desc", descriptor1.getDescriptor());

        RestEfferentDescriptor descriptor2 = iterator.next();
        Assert.assertEquals("restEfferent2", descriptor2.getId());
        Assert.assertEquals("ip2", descriptor2.getIp());
        Assert.assertEquals(24, descriptor2.getPort().intValue());
        Assert.assertEquals("restEfferent2 desc", descriptor2.getDescriptor());
    }

    @Test
    public void remove() throws Exception {
        fileRestEfferentStore.save(new RestEfferentDescriptor("restEfferent1", "ip1", 12, "restEfferent desc"));
        fileRestEfferentStore.save(new RestEfferentDescriptor("restEfferent2", "ip2", 24, "restEfferent2 desc"));
        fileRestEfferentStore.remove("restEfferent1");
        Set<RestEfferentDescriptor> efferents = fileRestEfferentStore.loadEfferentDescriptors();
        Assert.assertEquals(1, efferents.size());

        Iterator<RestEfferentDescriptor> iterator = efferents.iterator();

        RestEfferentDescriptor descriptor2 = iterator.next();
        Assert.assertEquals("restEfferent2", descriptor2.getId());
        Assert.assertEquals("ip2", descriptor2.getIp());
        Assert.assertEquals(24, descriptor2.getPort().intValue());
        Assert.assertEquals("restEfferent2 desc", descriptor2.getDescriptor());
    }
}
