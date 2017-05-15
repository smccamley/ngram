package brach.stefan.ngram.dto;

/**
 * Dto containing word of which probability in google books should be calculated.
 */
public class WordRequestDto {
    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
