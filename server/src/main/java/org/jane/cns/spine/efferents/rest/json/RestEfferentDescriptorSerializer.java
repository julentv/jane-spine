package org.jane.cns.spine.efferents.rest.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.jane.cns.spine.efferents.rest.RestEfferentDescriptor;

import java.io.IOException;

public class RestEfferentDescriptorSerializer extends StdSerializer<RestEfferentDescriptor> {
    public RestEfferentDescriptorSerializer() {
        super(RestEfferentDescriptor.class);
    }

    @Override
    public void serialize(RestEfferentDescriptor descriptor, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("id", descriptor.getId());
        jgen.writeStringField("descriptor", descriptor.getDescriptor());
        jgen.writeStringField("ip", descriptor.getIp());
        jgen.writeNumberField("port", descriptor.getPort());
        jgen.writeEndObject();
    }
}
