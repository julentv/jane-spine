package org.jane.cns.spine.efferents.rest.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.jane.cns.spine.efferents.rest.RestEfferentDescriptor;

import java.io.IOException;

public class RestEfferentDescriptorDeserializer extends StdDeserializer<RestEfferentDescriptor> {
    public RestEfferentDescriptorDeserializer() {
        super(RestEfferentDescriptor.class);
    }

    @Override
    public RestEfferentDescriptor deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String id = node.get("id").asText();
        String ip = node.get("ip").asText();
        Integer port = node.get("port").asInt();
        String descriptor = node.get("descriptor").asText();

        return new RestEfferentDescriptor(id, ip, port, descriptor);
    }
}
