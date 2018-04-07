package org.jane.cns.spine.efferents.rest;

import org.jane.cns.spine.test.server.MockServer;
import org.junit.After;
import org.junit.Before;

public class RestEfferentTestEnvironment {
    MockServer mockServer;
    RestEfferent restEfferent;

    @Before
    public void setUp() throws Exception {
        mockServer = new MockServer();
        int port = mockServer.connectServerToPort();
        restEfferent = new RestEfferent(new RestEfferentDescriptor("id", "http://localhost", port, "testDescriptor"));
    }

    @After
    public void tearDown() throws Exception {
        mockServer.closeServer();
    }
}
