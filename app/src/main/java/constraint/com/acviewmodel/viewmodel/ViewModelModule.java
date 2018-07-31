package constraint.com.acviewmodel.viewmodel;

import android.arch.lifecycle.ViewModel;

import constraint.com.acviewmodel.home.ListViewModel;
import constraint.com.acviewmodel.home.SelectedRepoViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Innovify on 31/7/18.
 */
@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.class)
    abstract ViewModel bindListViewModel(ListViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SelectedRepoViewModel.class)
    abstract ViewModel bindSelectedRepoViewModel(SelectedRepoViewModel listViewModel);
}
