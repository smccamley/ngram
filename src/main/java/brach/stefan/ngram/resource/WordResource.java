package brach.stefan.ngram.resource;

import brach.stefan.ngram.dto.WordRequestDto;
import brach.stefan.ngram.dto.WordResponseDto;
import brach.stefan.ngram.service.DataService;
import brach.stefan.ngram.service.InMemoryIndexService;
import com.google.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ngram")
public class WordResource {
    private final InMemoryIndexService inMemoryIndexService;
    private final DataService dataService;

    @Inject
    public WordResource(InMemoryIndexService inMemoryIndexService, DataService dataService) {
        this.inMemoryIndexService = inMemoryIndexService;
        this.dataService = dataService;
    }

    /**
     * Calculate probability that given word occurred in a book.
     */
    @POST
    @Path("/word")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProbability(WordRequestDto wordRequestDto) {
        Long count = inMemoryIndexService.getCount(wordRequestDto.getWord());
        WordResponseDto wordResponseDto = new WordResponseDto();
        wordResponseDto.setCount(count);
        wordResponseDto.setYear(dataService.getYear());
        wordResponseDto.setRelative(count / dataService.getTotalCount());
        return Response.ok(wordResponseDto).build();
    }
}
