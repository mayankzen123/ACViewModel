package constraint.com.acviewmodel.home;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import constraint.com.acviewmodel.R;
import constraint.com.acviewmodel.model.Repo;

/**
 * Created by Innovify on 31/7/18.
 */
public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoListViewHolder> {

    private final RepoSelectedListener repoSelectedListener;
    private List<Repo> repoArrayList = new ArrayList<>();

    RepoListAdapter(ListViewModel listViewModel, LifecycleOwner lifecycleOwner, RepoSelectedListener repoSelectedListener) {
        this.repoSelectedListener = repoSelectedListener;
        listViewModel.getRepos().observe(lifecycleOwner, new Observer<List<Repo>>() {
            @Override
            public void onChanged(@Nullable List<Repo> repos) {
                repoArrayList.clear();
                if (repos != null) {
                    //TODO when using AutoValue use Diff Util
                    repoArrayList.addAll(repos);
                }
            }
        });
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return repoArrayList.get(position).id;
    }

    @Override
    public RepoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_repo_list_item, parent, false);
        return new RepoListViewHolder(view, repoSelectedListener);
    }

    @Override
    public void onBindViewHolder(RepoListViewHolder holder, int position) {
        holder.bind(repoArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return repoArrayList.size();
    }

    static final class RepoListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_repo_name)
        TextView tvRepoName;
        @BindView(R.id.tv_description)
        TextView tvDescription;
        @BindView(R.id.tv_fork_count)
        TextView tvForkCount;
        @BindView(R.id.tv_star_count)
        TextView tvStarCount;
        private Repo repo;

        RepoListViewHolder(View itemView, RepoSelectedListener repoSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (repo != null) {
                    repoSelectedListener.onRepoSelected(repo);
                }
            });
        }

        void bind(Repo repo) {
            this.repo = repo;
            tvRepoName.setText(repo.name);
            tvDescription.setText(repo.description);
            tvForkCount.setText(String.valueOf(repo.forks));
            tvStarCount.setText(String.valueOf(repo.stars));
        }
    }
}
