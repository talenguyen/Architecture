package vn.tale.architecture.top_repos.transformer;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import vn.tale.architecture.common.mvvm.Action;
import vn.tale.architecture.common.mvvm.Function0;
import vn.tale.architecture.common.mvvm.Result;
import vn.tale.architecture.common.mvvm.Transformer;
import vn.tale.architecture.model.manager.RepoModel;
import vn.tale.architecture.top_repos.TopRepoListUiState;
import vn.tale.architecture.top_repos.action.LoadTopRepoAction;
import vn.tale.architecture.top_repos.result.RefreshTopRepoResult;

/**
 * Created by Giang Nguyen on 3/28/17.
 */

public class RefreshRepoListTransformer implements Transformer<TopRepoListUiState> {

  private final RepoModel repoModel;

  public RefreshRepoListTransformer(RepoModel repoModel) {
    this.repoModel = repoModel;
  }

  @Override public Observable<Result> transform(Observable<Action> action$,
      Function0<TopRepoListUiState> getState) {
    return action$
        .filter(action -> action == LoadTopRepoAction.REFRESH)
        .flatMap(ignored -> repoModel.getPublicRepos(true, 1)
            .toObservable()
            .map(RefreshTopRepoResult::success)
            .onErrorReturn(RefreshTopRepoResult::failure)
            .subscribeOn(Schedulers.io())
            .startWith(RefreshTopRepoResult.inProgress()));
  }
}
