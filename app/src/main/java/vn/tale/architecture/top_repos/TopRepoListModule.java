package vn.tale.architecture.top_repos;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import vn.tale.architecture.ActivityScope;
import vn.tale.architecture.common.redux.Store;
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
        .effects(
            new LoadEffect(repoModel),
            new RefreshEffect(repoModel),
            new LoadMoreEffect(repoModel))
        .reducer(new TopRepoListReducer())
        .make();
  }

  @Provides TopRepoListViewModel provideTopRepoListRenderer(Store<TopRepoListState> store) {
    final Observable<TopRepoListState> state$ = store.state$()
        .observeOn(AndroidSchedulers.mainThread());
    return new TopRepoListViewModel(state$);
  }
}
