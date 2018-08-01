package constraint.com.acviewmodel.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import constraint.com.acviewmodel.Details.DetailFragment;
import constraint.com.acviewmodel.R;
import constraint.com.acviewmodel.base.MyApplication;
import constraint.com.acviewmodel.model.Repo;
import constraint.com.acviewmodel.viewmodel.ViewModelFactory;

/**
 * Created by Innovify on 31/7/18.
 */
@SuppressWarnings("ALL")
public class ListFragment extends Fragment implements RepoSelectedListener {

    @Inject
    ViewModelFactory viewModelFactory;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    Unbinder unbinder;
    private ListViewModel listViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MyApplication.getApplicationComponent(context).inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        listViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel.class);
        rvList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvList.setAdapter(new RepoListAdapter(listViewModel, this, this));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        observeViewModel();
    }

    private void observeViewModel() {
        listViewModel.getRepoLoadError().observe(this, aBoolean -> {
            if (aBoolean) {
                tvError.setVisibility(View.VISIBLE);
                rvList.setVisibility(View.GONE);
                tvError.setText(R.string.error_loading);
            } else {
                tvError.setVisibility(View.GONE);
                tvError.setText(null);
            }
        });
        listViewModel.getRepoLoadingProgress().observe(this, aBoolean -> {
            progressBar.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
            if (aBoolean) {
                tvError.setVisibility(View.GONE);
                rvList.setVisibility(View.GONE);
            }
        });
        listViewModel.getRepos().observe(this, repos -> {
            if (repos != null) {
                rvList.setVisibility(View.VISIBLE);
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
    public void onRepoSelected(Repo repo) {
        SelectedRepoViewModel selectedRepoViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(SelectedRepoViewModel.class);
        selectedRepoViewModel.setSelectedLiveData(repo);
        getActivity().
                getSupportFragmentManager().
                beginTransaction().
                replace(R.id.screen_container, new DetailFragment()).
                addToBackStack(null).commit();
    }
}
