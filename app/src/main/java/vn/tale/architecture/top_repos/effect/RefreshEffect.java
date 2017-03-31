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
import vn.tale.architecture.top_repos.result.RefreshResult;

/**
 * Created by Giang Nguyen on 3/28/17.
 */

public class RefreshEffect implements Effect<TopRepoListState> {

  private final RepoModel repoModel;

  public RefreshEffect(RepoModel repoModel) {
    this.repoModel = repoModel;
  }

  @Override public Observable<Result> apply(Observable<Action> action$,
      Function0<TopRepoListState> getState) {
    return action$
        .filter(action -> action == LoadTopRepoAction.REFRESH)
        .flatMap(ignored -> repoModel.getPublicRepos(true, 1)
            .toObservable()
            .map(RefreshResult::success)
            .onErrorReturn(RefreshResult::failure)
            .subscribeOn(Schedulers.io())
            .startWith(RefreshResult.inProgress()));
  }
}
