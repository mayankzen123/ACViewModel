package constraint.com.acviewmodel.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * Created by Innovify on 31/7/18.
 */
@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {
    private Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModel;

    @Inject
    ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return (T) viewModel.get(modelClass).get();
        } catch (Exception e) {
            throw new RuntimeException("Error creating view model class" + modelClass.getSimpleName(), e);
        }
    }
}
