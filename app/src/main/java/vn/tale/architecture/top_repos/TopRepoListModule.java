package vn.tale.architecture.top_repos;

import dagger.Module;
import dagger.Provides;
import vn.tale.architecture.ActivityScope;
import vn.tale.architecture.common.reduxer.Store;
import vn.tale.architecture.model.manager.RepoModel;
import vn.tale.architecture.top_repos.effect.LoadMoreEffect;
import vn.tale.architecture.top_repos.effect.RefreshEffect;
import vn.tale.architecture.top_repos.effect.LoadEffect;

/**
 * Created by Giang Nguyen on 3/27/17.
 */
@Module
public class TopRepoListModule {

  @ActivityScope
  @Provides Store<TopRepoListState> provideViewModel(RepoModel repoModel) {
    return Store.<TopRepoListState>builder()
        .initialState(TopRepoListState.idle())
        .transformers(
            new LoadEffect(repoModel),
            new RefreshEffect(repoModel),
            new LoadMoreEffect(repoModel))
        .reducer(new TopRepoListReducer())
        .make();
  }
}
