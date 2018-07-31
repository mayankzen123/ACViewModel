package constraint.com.acviewmodel.networking;

import java.util.List;

import constraint.com.acviewmodel.model.Repo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Innovify on 31/7/18.
 */
public interface RepoService {
    @GET("orgs/Google/repos")
    Call<List<Repo>> getRepositories();

    @GET("repos/{owner}/{name}")
    Call<Repo> getRepo(@Path("owner") String owner, @Path("name") String name);
}
