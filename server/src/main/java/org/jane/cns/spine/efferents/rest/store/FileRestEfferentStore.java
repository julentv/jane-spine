package org.jane.cns.spine.efferents.rest.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.jane.cns.spine.efferents.rest.RestEfferentDescriptor;
import org.jane.cns.spine.efferents.rest.json.RestEfferentDescriptorDeserializer;
import org.jane.cns.spine.efferents.rest.json.RestEfferentDescriptorSerializer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FileRestEfferentStore implements RestEfferentStore {
    private static final String FILE_NAME = "RestEfferentDescriptors.json";

    private final ObjectMapper mapper;
    private final Path file;

    public FileRestEfferentStore() {
        this.mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(RestEfferentDescriptor.class, new RestEfferentDescriptorSerializer());
        module.addDeserializer(RestEfferentDescriptor.class, new RestEfferentDescriptorDeserializer());
        mapper.registerModule(module);
        this.file = Paths.get(FILE_NAME);
    }

    public Path getFile() {
        return file;
    }

    @Override
    public Set<RestEfferentDescriptor> loadEfferentDescriptors() {
        try {
            createFileIfNotExists();
            List<String> efferentsJsons = Files.readAllLines(file);
            return efferentsJsons.stream().map(this::deserializeRestEfferentDescriptor).collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RestEfferentsLoadException(e);
        }
    }

    @Override
    public void save(RestEfferentDescriptor efferentDescriptor) throws RestEfferentSaveException {
        try {
            createFileIfNotExists();
            List<String> efferentJsons = Files.readAllLines(file);
            efferentJsons.add(mapper.writeValueAsString(efferentDescriptor));
            Files.write(file, efferentJsons, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RestEfferentSaveException(e);
        }
    }

    @Override
    public void remove(String efferentId) {
        try {
            createFileIfNotExists();
            List<String> efferentJsons = Files.readAllLines(file);
            List<String> filteredEfferents = efferentJsons.stream()
                    .filter(efferent -> !deserializeRestEfferentDescriptor(efferent).getId().equals(efferentId))
                    .collect(Collectors.toList());
            Files.write(file, filteredEfferents, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RestEfferentRemoveException(e);
        }
    }

    private RestEfferentDescriptor deserializeRestEfferentDescriptor(String json) {
        try {
            return mapper.readValue(json, RestEfferentDescriptor.class);
        } catch (IOException e) {
            throw new RestEfferentsLoadException(e);
        }
    }

    private void createFileIfNotExists() throws IOException {
        if (Files.notExists(file)) {
            Files.createFile(file);
        }
    }
}
