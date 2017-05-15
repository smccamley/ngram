package brach.stefan.ngram.configuration;

import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class NgramConfiguration extends Configuration {
    @Valid
    @NotNull
    private NgramSettings settings;

    public NgramSettings getSettings() {
        return settings;
    }
}
