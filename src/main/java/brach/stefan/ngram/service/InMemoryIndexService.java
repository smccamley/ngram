package brach.stefan.ngram.service;

import java.io.IOException;

public interface InMemoryIndexService {
    void createHashMapsFromResourceDirectory() throws IOException;

    void createHashMapsFromExternalDirectory() throws IOException;

    long getCount(String str);
}
