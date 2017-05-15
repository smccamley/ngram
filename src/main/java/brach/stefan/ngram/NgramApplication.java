package brach.stefan.ngram;

import brach.stefan.ngram.configuration.NgramConfiguration;
import brach.stefan.ngram.configuration.NgramSettings;
import brach.stefan.ngram.resource.CreateIndexFilesResource;
import brach.stefan.ngram.resource.WordResource;
import brach.stefan.ngram.service.*;
import brach.stefan.ngram.service.impl.*;
import com.google.inject.*;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.IOException;

public class NgramApplication extends Application<NgramConfiguration> {
    public static void main(String[] args) throws Exception {
        new NgramApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<NgramConfiguration> bootstrap) {
    }

    @Override
    public void run(NgramConfiguration conf, Environment env) throws IOException {
        Injector injector = createInjector(conf, env);
        if (conf.getSettings().getProduction()) {
            // If in production, load index files from resources into memory and register endpoint to return relative
            // occurrence of word.
            injector.getInstance(InMemoryIndexService.class).createHashMapsFromResourceDirectory();
            registerResource(env, injector, WordResource.class);
        } else {
            // If not in production, register endpoints to download google ngram data and create index files from those.
            registerResource(env, injector, CreateIndexFilesResource.class);
        }
    }

    private void registerResource(Environment env, Injector injector, Class<?> theClass) {
        env.jersey().register(injector.getInstance(theClass));
    }

    private Injector createInjector(NgramConfiguration conf, Environment env) {
        return Guice.createInjector(Stage.DEVELOPMENT, new AbstractModule() {
            @Override
            protected void configure() {
                bind(NgramSettings.class).toInstance(conf.getSettings());
                bind(DownloadService.class).to(DownloadServiceImpl.class).in(Scopes.SINGLETON);
                bind(ExtractService.class).to(ExtractServiceImpl.class).in(Scopes.SINGLETON);
                bind(FileIndexService.class).to(FileIndexServiceImpl.class).in(Scopes.SINGLETON);
                bind(InMemoryIndexService.class).to(InMemoryIndexServiceImpl.class).in(Scopes.SINGLETON);
                bind(DataService.class).to(DataServiceImpl.class).in(Scopes.SINGLETON);
            }
        });
    }
}
