package brach.stefan.ngram.service.impl;

import brach.stefan.ngram.service.DataService;
import brach.stefan.ngram.service.DownloadService;
import com.google.inject.Inject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Service implementation that downloads ngram data from google.
 */
public class DownloadServiceImpl implements DownloadService {
    private final DataService dataService;

    @Inject
    public DownloadServiceImpl(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public void download() throws IOException {
        for (String name : dataService.getNames()) {
            URL url = new URL(dataService.getGoogleUrl(name));
            FileUtils.copyURLToFile(url, new File(dataService.getDownloadGzipPath(name)));
        }
    }
}
