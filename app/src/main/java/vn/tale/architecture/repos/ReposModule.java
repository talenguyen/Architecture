package vn.tale.architecture.repos;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.tale.architecture.ActivityScope;
import vn.tale.architecture.common.SchedulerObservableTransformer;
import vn.tale.architecture.model.manager.RepoModel;
import vn.tale.architecture.model.manager.UserModel;
import vn.tale.architecture.repos.menu.bottom.BottomMenuPresenter;
import vn.tale.architecture.repos.menu.top.TopMenuPresenter;
import vn.tale.architecture.repos.my.MyReposViewModel;
import vn.tale.architecture.repos.pub.PublicReposModel;
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

  @ActivityScope @Provides PublicReposModel providePublicReposModel(RepoModel repoModel) {
    return new PublicReposModel(repoModel);
  }

  @ActivityScope @Provides PublicReposPresenter providePublicReposPresenter(
      PublicReposModel repoModel) {
    return new PublicReposPresenter(
        repoModel,
        Schedulers.io(),
        AndroidSchedulers.mainThread(),
        false);
  }

  @ActivityScope @Provides MyReposViewModel provideViewModel(UserModel userModel,
      RepoModel repoModel, SchedulerObservableTransformer schedulerObservableTransformer) {
    return new MyReposViewModel(userModel, repoModel, schedulerObservableTransformer);
  }
}
