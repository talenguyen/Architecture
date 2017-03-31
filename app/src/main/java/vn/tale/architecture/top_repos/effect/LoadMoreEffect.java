package vn.tale.architecture.top_repos.effect;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import vn.tale.architecture.common.redux.Action;
import vn.tale.architecture.common.redux.Effect;
import vn.tale.architecture.common.redux.Function0;
import vn.tale.architecture.common.redux.Result;
import vn.tale.architecture.model.manager.RepoModel;
import vn.tale.architecture.top_repos.TopRepoListState;
import vn.tale.architecture.top_repos.action.LoadTopRepoAction;
import vn.tale.architecture.top_repos.result.LoadMoreResult;

/**
 * Created by Giang Nguyen on 3/28/17.
 */

public class LoadMoreEffect implements Effect<TopRepoListState> {

  private final RepoModel repoModel;

  public LoadMoreEffect(RepoModel repoModel) {
    this.repoModel = repoModel;
  }

  @Override public Observable<Result> apply(Observable<Action> action$,
      Function0<TopRepoListState> getState) {
    return action$
        .filter(action -> action == LoadTopRepoAction.LOAD_MORE)
        .filter(ignored -> !getState.apply().loadingMore())
        .flatMap(ignored -> repoModel.getPublicRepos(true, getState.apply().page() + 1)
            .toObservable()
            .map(content -> LoadMoreResult.success(getState.apply().page() + 1, content))
            .onErrorReturn(LoadMoreResult::failure)
            .subscribeOn(Schedulers.io())
            .startWith(LoadMoreResult.inProgress()));
  }
}
