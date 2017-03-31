package vn.tale.architecture.top_repos.effect;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import vn.tale.architecture.common.redux.Action;
import vn.tale.architecture.common.redux.Function0;
import vn.tale.architecture.common.redux.Result;
import vn.tale.architecture.common.redux.Effect;
import vn.tale.architecture.model.manager.RepoModel;
import vn.tale.architecture.top_repos.TopRepoListState;
import vn.tale.architecture.top_repos.action.LoadTopRepoAction;
import vn.tale.architecture.top_repos.result.LoadResult;

/**
 * Created by Giang Nguyen on 3/28/17.
 */

public class LoadEffect implements Effect<TopRepoListState> {

  private final RepoModel repoModel;

  public LoadEffect(RepoModel repoModel) {
    this.repoModel = repoModel;
  }

  @Override public Observable<Result> apply(Observable<Action> action$,
      Function0<TopRepoListState> getState) {
    return action$
        .filter(action -> action == LoadTopRepoAction.LOAD)
        .filter(ignored -> getState.apply().content().isEmpty())
        .flatMap(ignored -> repoModel.getPublicRepos(false, 1)
            .toObservable()
            .map(LoadResult::success)
            .onErrorReturn(LoadResult::failure)
            .subscribeOn(Schedulers.io())
            .startWith(LoadResult.inProgress()));
  }
}
