package vn.tale.architecture;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import vn.tale.architecture.common.AppRouter;
import vn.tale.architecture.model.manager.RepoModel;
import vn.tale.architecture.model.manager.UserModel;

/**
 * Created by Giang Nguyen on 2/27/17.
 */
@Module class AppSingletonModule {

  @Provides
  @Singleton
  public UserModel provideUserModel() {
    return new UserModel();
  }

  @Provides
  @Singleton
  public RepoModel provideRepoModel() {
    return new RepoModel();
  }

  @Provides
  @Singleton
  public AppRouter provideAppRouter() {
    return new AppRouter();
  }

}