package brach.stefan.ngram.service.impl;

import brach.stefan.ngram.data.SpeechTypes;
import brach.stefan.ngram.service.DataService;
import brach.stefan.ngram.service.InMemoryIndexService;
import com.google.inject.Inject;
import com.koloboke.collect.map.hash.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Service implementation that creates in memory hashmaps from index files that are either stored in the resource
 * folder or in an external directory. Subsequently, service provides method to calculate word occurrence probability
 * in google books for a given year.
 */
public class InMemoryIndexServiceImpl implements InMemoryIndexService {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryIndexServiceImpl.class);
    private final DataService dataService;
    /**
     * Contains absolute occurrence of words starting with a special character, i.e. no number or letter.
     */
    private HashObjLongMap<String> specialCharacterMap = HashObjLongMaps.getDefaultFactory().withNullKeyAllowed
            (false).newMutableMap();
    /**
     * Maps each number and letter from a-z to a map, where the map contains the occurrence of words starting with that
     * character.
     */
    private HashCharObjMap<HashObjIntMap<String>> numberOrLetterMaps = HashCharObjMaps.getDefaultFactory()
            .newMutableMap();

    @Inject
    public InMemoryIndexServiceImpl(DataService dataService) {
        this.dataService = dataService;
    }

    /**
     * Return an immutable String to Integer map with the initial content.
     */
    @SuppressWarnings("unchecked")
    private HashObjIntMap<String> createImmutableMap(Map initial) {
        return HashObjIntMaps.getDefaultFactory().withNullKeyAllowed(false).newImmutableMap(initial);
    }

    /**
     * Return an immutable String to Integer map with the given initial size.
     */
    private HashObjIntMap<String> createMap(int initSize) {
        return HashObjIntMaps.getDefaultFactory().withNullKeyAllowed(false).newMutableMap(initSize);
    }

    /**
     * Create in memory hashmaps that contain the occurrence of words from index files in the resource directory.
     */
    @Override
    public void createHashMapsFromResourceDirectory() throws IOException {
        createHashMaps(true);
    }

    /**
     * Create in memory hashmaps that contain the occurrence of words from index files in an external directory.
     */
    @Override
    public void createHashMapsFromExternalDirectory() throws IOException {
        createHashMaps(false);
    }

    /**
     * Create in memory hashmaps that contain the occurrence of words from index files.
     */
    private void createHashMaps(boolean fromResources) throws IOException {
        for (String name : dataService.getNames()) {
            if (name.equals("pos")) {
                continue;
            }
            String indexFileName = getIndexFileName(fromResources, name);
            LOG.info("loading file into memory: " + indexFileName);
            if (name.length() == 1) {
                HashObjIntMap<String> myMap = createMap((int) countLines(indexFileName));
                parse(myMap, indexFileName, name);
                numberOrLetterMaps.put(name.charAt(0), createImmutableMap(myMap));
            } else {
                parse(specialCharacterMap, indexFileName, name);
            }
        }
    }

    private String getIndexFileName(boolean fromResources, String name) {
        //if (fromResources) {
        //    return dataService.getResourceIndexPath(name);
        //} else {
        return dataService.getFileIndexPath(name);
        //}
    }

    /**
     * Read all words together with their absolute count from an index file and store those in hashmaps.
     */
    @SuppressWarnings("unchecked")
    private void parse(Map map, String indexFileName, String name) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(indexFileName))) {
            stream.forEach(line -> {
                String[] ngramCount = line.split(" ");
                String ngram = ngramCount[0];
                int underscoreIndex = ngram.lastIndexOf("_");
                if (underscoreIndex > -1) {
                    String speech = ngram.substring(underscoreIndex);
                    if (SpeechTypes.types.contains(speech)) {
                        ngram = ngram.substring(0, underscoreIndex);
                    }
                }
                if (name.length() == 1) {
                    map.put(ngram, Integer.parseInt(ngramCount[1]));
                } else {
                    map.put(ngram, Long.parseLong(ngramCount[1]));
                }
            });
        }
    }

    /**
     * Return absolute occurrence of word in google books
     */
    @Override
    @SuppressWarnings("ConstantConditions")
    public long getCount(String str) {
        if (StringUtils.isBlank(str)) {
            return 0L;
        } else {
            char character = Character.toLowerCase(str.charAt(0));
            if (((character >= '0' && character <= '9') || character >= 'a' && character <= 'z')) {
                return numberOrLetterMaps.get(character).getInt(str);
            } else {
                return specialCharacterMap.getLong(str);
            }
        }
    }

    /**
     * Returns number of lines in file
     */
    private long countLines(String fileName) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            return stream.count();
        }
    }
}

