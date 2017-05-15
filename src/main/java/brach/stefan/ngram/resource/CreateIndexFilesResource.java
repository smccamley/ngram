package brach.stefan.ngram.resource;

import brach.stefan.ngram.service.DownloadService;
import brach.stefan.ngram.service.ExtractService;
import brach.stefan.ngram.service.FileIndexService;
import brach.stefan.ngram.service.InMemoryIndexService;
import com.google.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Endpoints to download and create index files from google ngram data.
 */
@Path("/ngram")
public class CreateIndexFilesResource {
    private final DownloadService downloadService;
    private final ExtractService extractService;
    private final FileIndexService fileIndexService;
    private final InMemoryIndexService inMemoryIndexService;

    @Inject
    public CreateIndexFilesResource(DownloadService downloadService, ExtractService extractService, FileIndexService
            fileIndexService, InMemoryIndexService inMemoryIndexService) {
        this.downloadService = downloadService;
        this.extractService = extractService;
        this.fileIndexService = fileIndexService;
        this.inMemoryIndexService = inMemoryIndexService;
    }

    /**
     * Download ngram files from google, extract those, create individual index files and in memory hashmaps.
     */
    @POST
    @Path("/all")
    public Response all() throws IOException {
        downloadService.download();
        extractService.extract();
        fileIndexService.buildIndexFile();
        inMemoryIndexService.createHashMapsFromExternalDirectory();
        return Response.ok().build();
    }

    /**
     * Download ngram files from google.
     */
    @POST
    @Path("/download")
    public Response download() throws IOException {
        downloadService.download();
        return Response.ok().build();
    }

    /**
     * Extract downloaded ngram files from google.
     */
    @POST
    @Path("/extract")
    public Response extract() throws IOException {
        extractService.extract();
        return Response.ok().build();
    }

    /**
     * Create index files from extracted google ngram files.
     */
    @POST
    @Path("/index/file")
    public Response createIndexFiles() throws IOException {
        fileIndexService.buildIndexFile();
        return Response.ok().build();
    }

    /**
     * Create in memory hashmaps from index files saved in external directory.
     */
    @GET
    @Path("/index/memory")
    public Response createInMemoryIndex() throws IOException {
        inMemoryIndexService.createHashMapsFromExternalDirectory();
        return Response.ok().build();
    }
}
