package constraint.com.acviewmodel.networking;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by Innovify on 31/7/18.
 */
public class RepoApi {
    private static final String BASE_URL = "https://api.github.com/";
    private static Retrofit retrofit;
    private static RepoService repoService;

    public static RepoService getInstance() {
        if (repoService != null) {
            return repoService;
        }
        if (retrofit == null) {
            intializeRetrofit();
        }
        repoService = retrofit.create(RepoService.class);
        return repoService;
    }

    public static void intializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    private RepoApi() {

    }
}
