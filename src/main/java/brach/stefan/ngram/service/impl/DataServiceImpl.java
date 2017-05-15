package brach.stefan.ngram.service.impl;

import brach.stefan.ngram.configuration.NgramSettings;
import brach.stefan.ngram.service.DataService;
import com.google.inject.Inject;
import io.dropwizard.testing.ResourceHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Service implementation that contains all relevant data links to create index files and in memory hashmaps for 2008.
 */
public class DataServiceImpl implements DataService {
    private final static String YEAR = "2008";
    private final static Double TOTAL_COUNT = Double.parseDouble("19482936409108811006206272");
    private final static String GOOGLE_ADDRESS_BASE = "http://storage.googleapis.com/books/ngrams/books/";
    private final static String GOOGLE_ADDRESS_ENDING = "googlebooks-eng-all-1gram-20120701-";
    private final static String NUMBERS = "0 1 2 3 4 5 6 7 8 9";
    private final static String LETTERS = "a b c d e f g h i j k l m n o p q r s t u v w x y z";
    private final static String OTHER = "other pos punctuation";
    private final static List<String> NAMES;
    private final static String DOWNLOAD_DIR = "download/";
    private final static String EXTRACT_DIR = "extract/";
    private final static String INDEX_DIR = "index/";

    static {
        List<String> names = new ArrayList<>();
        names.addAll(Arrays.asList(NUMBERS.split(" ")));
        names.addAll(Arrays.asList(LETTERS.split(" ")));
        names.addAll(Arrays.asList(OTHER.split(" ")));
        NAMES = Collections.unmodifiableList(names);
    }

    private final String basePath;

    @Inject
    public DataServiceImpl(NgramSettings ngramSettings) {
        this.basePath = ngramSettings.getDownloadDir();
    }

    @Override
    public Double getTotalCount() {
        return TOTAL_COUNT;
    }

    @Override
    public String getYear() {
        return YEAR;
    }

    @Override
    public List<String> getNames() {
        return NAMES;
    }

    @Override
    public String getResourceIndexPath(String name) {
        return ResourceHelpers.resourceFilePath(String.format("%s%s%s.txt", INDEX_DIR, GOOGLE_ADDRESS_ENDING, name));
    }

    @Override
    public String getFileIndexPath(String name) {
        return String.format("%s%s%s%s.txt", basePath, INDEX_DIR, GOOGLE_ADDRESS_ENDING, name);
    }

    @Override
    public String getExtractPath(String name) {
        return String.format("%s%s%s%s.txt", basePath, EXTRACT_DIR, GOOGLE_ADDRESS_ENDING, name);
    }

    @Override
    public String getDownloadGzipPath(String name) {
        return String.format("%s%s%s%s.gz", basePath, DOWNLOAD_DIR, GOOGLE_ADDRESS_ENDING, name);
    }

    @Override
    public String getGoogleUrl(String name) {
        return String.format("%s%s%s.gz", GOOGLE_ADDRESS_BASE, GOOGLE_ADDRESS_ENDING, name);
    }
}
