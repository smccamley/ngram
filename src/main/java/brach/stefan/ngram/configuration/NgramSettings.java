package brach.stefan.ngram.configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class NgramSettings {
    /**
     * Base directory for all other directories to store ngram index files.
     */
    @Valid
    @NotNull
    private String downloadDir;
    /**
     * If true, server will expose endpoints to calculate word probability.
     * If false, server will expose endpoints to create ngram index files.
     */
    @Valid
    @NotNull
    private Boolean production;

    public String getDownloadDir() {
        return downloadDir;
    }

    public Boolean getProduction() {
        return production;
    }
}
