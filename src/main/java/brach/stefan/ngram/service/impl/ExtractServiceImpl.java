package brach.stefan.ngram.service.impl;

import brach.stefan.ngram.service.DataService;
import brach.stefan.ngram.service.ExtractService;
import com.google.inject.Inject;
import org.rauschig.jarchivelib.Compressor;
import org.rauschig.jarchivelib.CompressorFactory;

import java.io.File;
import java.io.IOException;

/**
 * Service implementation that extracts the previously downloaded ngram data gzip files from google.
 */
public class ExtractServiceImpl implements ExtractService {
    private final DataService dataService;

    @Inject
    public ExtractServiceImpl(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public void extract() throws IOException {
        for (String name : dataService.getNames()) {
            File origin = new File(dataService.getDownloadGzipPath(name));
            File destination = new File(dataService.getExtractPath(name));
            if (!destination.getParentFile().exists()) {
                destination.getParentFile().mkdirs();
            }
            Compressor compressor = CompressorFactory.createCompressor(origin);
            compressor.decompress(origin, destination);
        }
    }
}
