package brach.stefan.ngram.service.impl;

import brach.stefan.ngram.service.DataService;
import brach.stefan.ngram.service.FileIndexService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

/**
 * Service implementation that creates index files from extracted google ngram data.
 */
public class FileIndexServiceImpl implements FileIndexService {
    private static final Logger LOG = LoggerFactory.getLogger(FileIndexServiceImpl.class);
    private final DataService dataService;

    @Inject
    public FileIndexServiceImpl(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public void buildIndexFile() throws IOException {
        for (String name : dataService.getNames()) {
            String indexFileName = dataService.getFileIndexPath(name);
            File indexFile = new File(indexFileName);
            if (indexFile.exists()) {
                String errorMessage = String.format("Index file %s already exists", name);
                LOG.error(errorMessage);
                throw new RuntimeException(errorMessage);
            }
            indexFile.getParentFile().mkdirs();
            indexFile.createNewFile();
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(indexFileName), StandardOpenOption.APPEND)) {
                String fileName = dataService.getExtractPath(name);
                try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
                    stream.forEach(line -> {
                        String[] content = line.split("\\s+");
                        String ngram = content[0];
                        String year = content[1];
                        String matchCount = content[2];
                        if (dataService.getYear().equals(year)) {
                            try {
                                writer.write(ngram + " " + matchCount + "\n");
                            } catch (IOException e) {
                                String errorMessage = "Unable to write string to file";
                                throw new RuntimeException(errorMessage, e);
                            }
                        }
                    });
                }
            }
        }
    }
}
