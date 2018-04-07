package org.jane.cns.spine.efferents.rest;

import org.apache.log4j.Logger;
import org.jane.cns.spine.efferents.Efferent;
import org.jane.cns.spine.efferents.EfferentStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestEfferent implements Efferent {
    private static final Logger LOGGER = Logger.getLogger(RestEfferent.class);

    private static final String EFFERENT_ACTIVATE_URL = "/efferent/activate";
    private static final String EFFERENT_STATUS = "/efferent/status";
    private static final String EFFERENT_INHIBIT_URL = "/efferent/inhibit";
    private final RestEfferentDescriptor efferentDescriptor;

    public RestEfferent(RestEfferentDescriptor efferentDescriptor) {
        this.efferentDescriptor = efferentDescriptor;
    }

    @Override
    public RestEfferentDescriptor getEfferentDescriptor() {
        return efferentDescriptor;
    }

    @Override
    public EfferentStatus getEfferentStatus() {
        try {
            URL url = new URL(efferentDescriptor.getIpAndPort() + EFFERENT_STATUS);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (200 != responseCode) {
                return EfferentStatus.FAILED;
            }
            String content = getMethodMessage(con);
            con.disconnect();
            return EfferentStatus.valueOf(content);
        } catch (IOException e) {
            LOGGER.error(e);
            return EfferentStatus.OFFLINE;
        }
    }

    private String getMethodMessage(HttpURLConnection con) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
        }
        return content.toString();
    }

    @Override
    public void activate() throws RestEfferentCouldNotBeActivatedException {
        try {
            int responseCode = getResponseCodeFromUrl(efferentDescriptor.getIpAndPort() + EFFERENT_ACTIVATE_URL);
            if (200 != responseCode) {
                throw new RestEfferentCouldNotBeActivatedException("Received response code: " + responseCode);
            }
        } catch (IOException e) {
            throw new RestEfferentCouldNotBeActivatedException(e);
        }
    }

    private int getResponseCodeFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        con.disconnect();
        return responseCode;
    }

    @Override
    public void inhibit() {
        try {
            int responseCode = getResponseCodeFromUrl(efferentDescriptor.getIpAndPort() + EFFERENT_INHIBIT_URL);
            if (200 != responseCode) {
                throw new RestEfferentCouldNotBeInhibitedException("Received response code: " + responseCode);
            }
        } catch (IOException e) {
            throw new RestEfferentCouldNotBeInhibitedException(e);
        }
    }
}
