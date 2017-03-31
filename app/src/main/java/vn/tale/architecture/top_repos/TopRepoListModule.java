package vn.tale.architecture.top_repos;

import dagger.Module;
import dagger.Provides;
import vn.tale.architecture.ActivityScope;
import vn.tale.architecture.common.mvvm.Store;
import vn.tale.architecture.model.manager.RepoModel;
import vn.tale.architecture.top_repos.transformer.LoadMoreTransformer;
import vn.tale.architecture.top_repos.transformer.RefreshRepoListTransformer;
import vn.tale.architecture.top_repos.transformer.TopRepoListTransformer;

/**
 * Created by Giang Nguyen on 3/27/17.
 */
@Module
public class TopRepoListModule {

  @ActivityScope
  @Provides Store<TopRepoListUiState> provideViewModel(RepoModel repoModel) {
    return Store.<TopRepoListUiState>builder()
        .initialState(TopRepoListUiState.idle())
        .transformers(
            new TopRepoListTransformer(repoModel),
            new RefreshRepoListTransformer(repoModel),
            new LoadMoreTransformer(repoModel))
        .reducer(new TopRepoListReducer())
        .make();
  }
}
