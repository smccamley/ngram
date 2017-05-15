package brach.stefan.ngram.service;

import java.util.List;

public interface DataService {
    Double getTotalCount();

    String getYear();

    List<String> getNames();

    String getResourceIndexPath(String name);

    String getFileIndexPath(String name);

    String getExtractPath(String name);

    String getDownloadGzipPath(String name);

    String getGoogleUrl(String name);
}
