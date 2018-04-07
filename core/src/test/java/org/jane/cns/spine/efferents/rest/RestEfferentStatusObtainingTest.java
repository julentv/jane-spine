package org.jane.cns.spine.efferents.rest;

import org.jane.cns.spine.efferents.EfferentStatus;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class RestEfferentStatusObtainingTest extends RestEfferentTestEnvironment {

    @Test
    public void efferentStatusSameAsRespondedWhenCorrectServerResponse() {
        mockServer.setResponse(200, "ACTIVE");

        Assert.assertEquals(EfferentStatus.ACTIVE, restEfferent.getEfferentStatus());
        Assert.assertEquals("/efferent/status", mockServer.getLastRequest().getAddress().getPath().toString());
    }

    @Test
    public void efferentStatusFailedWhenServerErrorResponse() {
        mockServer.setResponse(400, "");

        Assert.assertEquals(EfferentStatus.FAILED, restEfferent.getEfferentStatus());
        Assert.assertEquals("/efferent/status", mockServer.getLastRequest().getAddress().getPath().toString());
    }

    @Test
    public void efferentStatusOflineWhenServerErrorDown() throws IOException {
        mockServer.closeServer();
        Assert.assertEquals(EfferentStatus.OFFLINE, restEfferent.getEfferentStatus());
    }
}
