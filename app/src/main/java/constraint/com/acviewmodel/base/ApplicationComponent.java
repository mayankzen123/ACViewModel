package constraint.com.acviewmodel.base;

import javax.inject.Singleton;

import constraint.com.acviewmodel.networking.NetworkingModule;
import constraint.com.acviewmodel.viewmodel.ViewModelModule;
import dagger.Component;

/**
 * Created by Innovify on 31/7/18.
 */
@Singleton
@Component(modules = {NetworkingModule.class, ViewModelModule.class})
public interface ApplicationComponent {
}
