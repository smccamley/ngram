package brach.stefan.ngram.dto;

/**
 * Dto containing absolute count as well as relative occurrence of word in google books in given year.
 */
public class WordResponseDto {
    public long count;
    public double relative;
    public String year;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getRelative() {
        return relative;
    }

    public void setRelative(double relative) {
        this.relative = relative;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
