package vn.tale.architecture;

import dagger.Component;
import javax.inject.Singleton;
import vn.tale.architecture.counter.CounterComponent;
import vn.tale.architecture.counter.CounterModule;
import vn.tale.architecture.login.LoginComponent;
import vn.tale.architecture.login.LoginModule;
import vn.tale.architecture.top_repos.TopRepoListComponent;
import vn.tale.architecture.top_repos.TopRepoListModule;

/**
 * Created by Giang Nguyen on 2/27/17.
 */
@Singleton
@Component(modules = {
    AppSingletonModule.class,
    AppModule.class
})
public interface AppComponent {

  LoginComponent plus(LoginModule loginModule);

  CounterComponent plus(CounterModule counterModule);

  TopRepoListComponent plus(TopRepoListModule topRepoListModule);
}
