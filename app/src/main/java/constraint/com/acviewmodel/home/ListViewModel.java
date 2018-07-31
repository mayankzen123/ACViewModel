package constraint.com.acviewmodel.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import constraint.com.acviewmodel.model.Repo;
import constraint.com.acviewmodel.networking.RepoApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Innovify on 31/7/18.
 */
public class ListViewModel extends ViewModel {
    private final MutableLiveData<List<Repo>> repos = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadingProgress = new MutableLiveData<>();
    private Call<List<Repo>> repositories;

    public ListViewModel() {
        fetchRepos();
    }

    public LiveData<List<Repo>> getRepos() {
        return repos;
    }

    public LiveData<Boolean> getRepoLoadError() {
        return repoLoadError;
    }

    public LiveData<Boolean> getRepoLoadingProgress() {
        return repoLoadingProgress;
    }

    private void fetchRepos() {
        repoLoadingProgress.setValue(true);
        repositories = RepoApi.getInstance().getRepositories();
        repositories.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(@NonNull Call<List<Repo>> call, @NonNull Response<List<Repo>> response) {
                repoLoadError.setValue(false);
                repoLoadingProgress.setValue(false);
                repos.setValue(response.body());
                repositories = null;
            }

            @Override
            public void onFailure(@NonNull Call<List<Repo>> call, @NonNull Throwable t) {
                Log.e(this.getClass().getSimpleName(), "Error loading Repo" + t.getMessage());
                repoLoadError.setValue(true);
                repoLoadingProgress.setValue(false);
                repositories = null;
            }
        });
    }

    @Override
    protected void onCleared() {
        if (repositories != null) {
            repositories.cancel();
            repositories = null;
        }
    }
}
