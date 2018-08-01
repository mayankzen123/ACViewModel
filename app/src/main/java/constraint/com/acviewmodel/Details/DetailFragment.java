package constraint.com.acviewmodel.Details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import constraint.com.acviewmodel.R;
import constraint.com.acviewmodel.base.MyApplication;
import constraint.com.acviewmodel.home.SelectedRepoViewModel;
import constraint.com.acviewmodel.model.Repo;
import constraint.com.acviewmodel.viewmodel.ViewModelFactory;

/**
 * Created by Innovify on 31/7/18.
 */
public class DetailFragment extends Fragment {
    @BindView(R.id.tv_repo_name)
    TextView tvRepoName;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_fork_count)
    TextView tvForkCount;
    @BindView(R.id.tv_star_count)
    TextView tvStarCount;
    Unbinder unbinder;
    private SelectedRepoViewModel viewModel;
    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MyApplication.getApplicationComponent(context).inject(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        displayInfo();
        viewModel.restoreFromBundle(savedInstanceState);
    }

    private void displayInfo() {
        viewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(SelectedRepoViewModel.class);
        viewModel.getRepoMutableLiveData().observe(this, new Observer<Repo>() {
            @Override
            public void onChanged(@Nullable Repo repo) {
                if (repo != null) {
                    tvRepoName.setText(repo.name);
                    tvDescription.setText(repo.description);
                    tvForkCount.setText(String.valueOf(repo.forks));
                    tvStarCount.setText(String.valueOf(repo.stars));
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        viewModel.saveDataToBundle(outState);
    }
}
