package org.jane.cns.spine.efferents.rest.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jane.cns.spine.efferents.rest.RestEfferentDescriptor;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileRestEfferentStore implements RestEfferentStore {
    private static final String DEFAULT_FILE_NAME = "store/RestEfferentDescriptors.json";

    private final ObjectMapper mapper;
    private final Path file;

    public FileRestEfferentStore(ObjectMapper mapper) {
        this(mapper, DEFAULT_FILE_NAME);
    }

    public FileRestEfferentStore(ObjectMapper mapper, String filePath) {
        this.mapper = mapper;
        this.file = Paths.get(filePath);
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
            Map<String, RestEfferentDescriptor> efferentDescriptors = Files.readAllLines(file).stream()
                    .map(this::deserializeRestEfferentDescriptor)
                    .collect(Collectors.toMap(RestEfferentDescriptor::getId, Function.identity()));

            efferentDescriptors.put(efferentDescriptor.getId(), efferentDescriptor);

            List<String> jsonEfferentsToSave = efferentDescriptors.values().stream()
                    .map(this::serializeRestEfferentDescriptor).collect(Collectors.toList());
            Files.write(file, jsonEfferentsToSave, Charset.forName("UTF-8"));
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

    private String serializeRestEfferentDescriptor(RestEfferentDescriptor descriptor) {
        try {
            return mapper.writeValueAsString(descriptor);
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
