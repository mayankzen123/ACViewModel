package constraint.com.acviewmodel.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import constraint.com.acviewmodel.model.Repo;
import constraint.com.acviewmodel.networking.RepoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Innovify on 31/7/18.
 */
public class SelectedRepoViewModel extends ViewModel {
    public final MutableLiveData<Repo> repoMutableLiveData = new MutableLiveData<>();
    private Call<Repo> repoApi;
    private static final String KEY_REPO_DETAILS = "repo_details";
    private RepoService repoService;

    @Inject
    public SelectedRepoViewModel(RepoService repoService) {
        this.repoService = repoService;
    }

    public LiveData<Repo> getRepoMutableLiveData() {
        return repoMutableLiveData;
    }

    void setSelectedLiveData(Repo repo) {
        repoMutableLiveData.setValue(repo);
    }

    public void saveDataToBundle(Bundle outState) {
        if (repoMutableLiveData.getValue() != null) {
            outState.putStringArray(KEY_REPO_DETAILS, new String[]{repoMutableLiveData.getValue().owner.login, repoMutableLiveData.getValue().name});
        }
    }

    public void restoreFromBundle(Bundle savedInstanceState) {
        //Only when repoMutableLiveData don't Contain data
        if (repoMutableLiveData.getValue() == null) {
            if (savedInstanceState != null && savedInstanceState.containsKey(KEY_REPO_DETAILS)) {
                loadRepo(savedInstanceState.getStringArray(KEY_REPO_DETAILS));
            }
        }
    }

    private void loadRepo(String[] repo_details) {
        repoApi = repoService.getRepo(repo_details[0], repo_details[1]);
        repoApi.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(@NonNull Call<Repo> call, @NonNull Response<Repo> response) {
                repoMutableLiveData.setValue(response.body());
                repoApi = null;
            }

            @Override
            public void onFailure(@NonNull Call<Repo> call, @NonNull Throwable t) {
                Log.e(this.getClass().getSimpleName(), t.getMessage());
                repoApi = null;
            }
        });
    }

    @Override
    protected void onCleared() {
        if (repoApi != null) {
            repoApi.cancel();
            repoApi = null;
        }
    }
}
