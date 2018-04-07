package org.jane.cns.spine.efferents.rest;

import org.junit.Assert;
import org.junit.Test;

public class RestEfferentActivateTest extends RestEfferentTestEnvironment {

    @Test
    public void canActivateEfferent() {
        mockServer.setResponse(200, "");
        restEfferent.activate();
        Assert.assertEquals("/efferent/activate", mockServer.getLastRequest().getAddress().getPath().toString());
    }

    @Test(expected = RestEfferentCouldNotBeActivatedException.class)
    public void exceptionThrownWhenRestServiceNotResponding() {
        mockServer.setResponse(400, "");
        restEfferent.activate();
    }

    @Test
    public void canInhibitEfferent() {
        mockServer.setResponse(200, "");
        restEfferent.inhibit();
        Assert.assertEquals("/efferent/inhibit", mockServer.getLastRequest().getAddress().getPath().toString());
    }
}
