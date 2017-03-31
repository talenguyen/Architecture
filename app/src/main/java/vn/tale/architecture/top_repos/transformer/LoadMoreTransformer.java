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
import vn.tale.architecture.top_repos.result.LoadMoreResult;

/**
 * Created by Giang Nguyen on 3/28/17.
 */

public class LoadMoreTransformer implements Transformer<TopRepoListUiState> {

  private final RepoModel repoModel;

  public LoadMoreTransformer(RepoModel repoModel) {
    this.repoModel = repoModel;
  }

  @Override public Observable<Result> transform(Observable<Action> action$,
      Function0<TopRepoListUiState> getState) {
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
