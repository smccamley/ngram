package brach.stefan.ngram.dto;

/**
 * Dto containing absolute count as well as relative occurrence of word in google books in given year.
 */
public class WordResponseDto {
    public long count;
    public double probability;
    public String year;
    public String word;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
