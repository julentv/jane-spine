package org.jane.cns.spine.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.jane.cns.spine.EfferentsManager;
import org.jane.cns.spine.efferents.EfferentStatus;
import org.jane.cns.spine.efferents.rest.RestEfferentCouldNotBeActivatedException;
import org.jane.cns.spine.efferents.rest.RestEfferentCouldNotBeInhibitedException;
import org.jane.cns.spine.efferents.rest.RestEfferentDescriptor;
import org.jane.cns.spine.efferents.rest.RestEfferentFactory;
import org.jane.cns.spine.efferents.rest.json.RestEfferentDescriptorDeserializer;
import org.jane.cns.spine.efferents.rest.json.RestEfferentDescriptorSerializer;
import org.jane.cns.spine.efferents.rest.store.FileRestEfferentStore;

import javax.annotation.PostConstruct;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/jane/efferents")
public class EfferentManagementService extends Application {
    @Context
    private ServletConfig context;

    private static final Logger LOGGER = Logger.getLogger(EfferentManagementService.class);
    private EfferentsManager efferentsManager;
    private final ObjectMapper mapper;

    public EfferentManagementService() {
        mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(RestEfferentDescriptor.class, new RestEfferentDescriptorSerializer());
        module.addDeserializer(RestEfferentDescriptor.class, new RestEfferentDescriptorDeserializer());
        mapper.registerModule(module);
    }

    @PostConstruct
    public void readInitParams() {
        String storePath = context.getInitParameter("org.jane.cns.spine.store.path");

        this.efferentsManager = new EfferentsManager(new RestEfferentFactory(), new FileRestEfferentStore(mapper, storePath));
    }

    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEfferentsStatus() throws JsonProcessingException {
        try {
            LOGGER.info("getting efferents status");
            return Response.ok(mapper.writeValueAsString(efferentsManager.getEfferentsStatus())).build();
        } catch (Exception e) {
            LOGGER.error("Error getting efferents", e);
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEfferents() throws JsonProcessingException {
        try {
            LOGGER.info("getting efferents");
            return Response.ok(mapper.writeValueAsString(efferentsManager.getEfferents())).build();
        } catch (Exception e) {
            LOGGER.error("Error getting efferents", e);
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{efferentId}/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEfferentsStatus(@PathParam("efferentId") String efferentId) throws JsonProcessingException {
        try {
            LOGGER.info("getting efferents" + efferentId + "status");
            Optional<EfferentStatus> efferentStatus = efferentsManager.getEfferentStatus(efferentId);
            if (efferentStatus.isPresent()) {
                return Response.ok(mapper.writeValueAsString(efferentStatus.get())).build();
            } else {
                return Response.status(400)
                        .entity(mapper.writeValueAsString(new ServiceException("No efferent with id: " + efferentId, "")))
                        .build();
            }
        } catch (Exception e) {
            LOGGER.error("Error getting efferent status", e);
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEfferent(String json) throws JsonProcessingException {
        try {
            RestEfferentDescriptor descriptor = mapper.readValue(json, RestEfferentDescriptor.class);
            LOGGER.info("adding efferent " + descriptor.getId());
            efferentsManager.addRestEfferent(descriptor);
            return Response.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error adding efferent", e);
            ServiceException serviceException = new ServiceException("Error adding efferent", ExceptionUtils.getStackTrace(e));
            return Response.status(500).entity(mapper.writeValueAsString(serviceException)).build();
        }
    }

    @GET
    @Path("/{efferentId}/activate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response activateEfferent(@PathParam("efferentId") String efferentId) throws JsonProcessingException {
        try {
            LOGGER.info("activating efferent" + efferentId);
            efferentsManager.activateEfferent(efferentId);
            return Response.ok().build();
        } catch (RestEfferentCouldNotBeActivatedException ex) {
            LOGGER.error("The efferent could not be activated", ex);
            ServiceException serviceException = new ServiceException("The efferent " + efferentId + " could not be activated", ExceptionUtils.getStackTrace(ex));
            return Response.serverError().entity(mapper.writeValueAsString(serviceException)).build();
        } catch (Exception e) {
            LOGGER.error("Error activating efferent", e);
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{efferentId}/inhibit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response inhibitEfferent(@PathParam("efferentId") String efferentId) throws JsonProcessingException {
        try {
            LOGGER.info("inhibiting efferent" + efferentId);
            efferentsManager.inhibitEfferent(efferentId);
            return Response.ok().build();
        } catch (RestEfferentCouldNotBeInhibitedException ex) {
            LOGGER.error("The efferent could not be inhibited", ex);
            ServiceException serviceException = new ServiceException("The efferent " + efferentId + " could not be inhibited", ExceptionUtils.getStackTrace(ex));
            return Response.serverError().entity(mapper.writeValueAsString(serviceException)).build();
        } catch (Exception e) {
            LOGGER.error("Error inhibiting efferent", e);
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{efferentId}/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEfferent(@PathParam("efferentId") String efferentId) {
        try {
            LOGGER.info("deleting efferent" + efferentId);
            efferentsManager.removeEfferent(efferentId);
            return Response.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error deleting efferent", e);
            return Response.serverError().build();
        }
    }
}
