package constraint.com.acviewmodel.networking;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by Innovify on 31/7/18.
 */
@Module
public abstract class NetworkingModule {
    private static final String BASE_URL = "https://api.github.com/";

    @Provides
    @Singleton
    static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    @Provides
    @Singleton
    static RepoService getRepoService(Retrofit retrofit) {
        return retrofit.create(RepoService.class);
    }
}
