package vn.tale.architecture.repos;

import dagger.Module;
import dagger.Provides;
import vn.tale.architecture.ActivityScope;
import vn.tale.architecture.common.SchedulerObservableTransformer;
import vn.tale.architecture.model.manager.RepoModel;
import vn.tale.architecture.model.manager.UserModel;
import vn.tale.architecture.repos.menu.bottom.BottomMenuPresenter;
import vn.tale.architecture.repos.menu.top.TopMenuPresenter;
import vn.tale.architecture.repos.my.MyReposPresenter;
import vn.tale.architecture.repos.pub.PublicReposPresenter;

/**
 * Created by Giang Nguyen on 2/27/17.
 */
@Module
public class ReposModule {

  @ActivityScope @Provides BottomMenuPresenter provideBottomMenuPresenter(UserModel userModel) {
    return new BottomMenuPresenter(userModel);
  }

  @ActivityScope @Provides TopMenuPresenter provideTopMenuPresenter(UserModel userModel) {
    return new TopMenuPresenter(userModel);
  }

  @ActivityScope @Provides PublicReposPresenter providePublicReposPresenter(RepoModel repoModel) {
    return new PublicReposPresenter(repoModel);
  }

  @ActivityScope @Provides MyReposPresenter provideMyReposPresenter(UserModel userModel,
      RepoModel repoModel,
      SchedulerObservableTransformer schedulerObservableTransformer) {
    return new MyReposPresenter(userModel, repoModel, schedulerObservableTransformer);
  }
}
