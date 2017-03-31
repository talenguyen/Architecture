package vn.tale.architecture.top_repos.transformer;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import vn.tale.architecture.common.reduxer.Action;
import vn.tale.architecture.common.reduxer.Function0;
import vn.tale.architecture.common.reduxer.Result;
import vn.tale.architecture.common.reduxer.Transformer;
import vn.tale.architecture.model.manager.RepoModel;
import vn.tale.architecture.top_repos.TopRepoListUiState;
import vn.tale.architecture.top_repos.action.LoadTopRepoAction;
import vn.tale.architecture.top_repos.result.LoadTopRepoResult;

/**
 * Created by Giang Nguyen on 3/28/17.
 */

public class TopRepoListTransformer implements Transformer<TopRepoListUiState> {

  private final RepoModel repoModel;

  public TopRepoListTransformer(RepoModel repoModel) {
    this.repoModel = repoModel;
  }

  @Override public Observable<Result> transform(Observable<Action> action$,
      Function0<TopRepoListUiState> getState) {
    return action$
        .filter(action -> action == LoadTopRepoAction.LOAD)
        .filter(ignored -> getState.apply().content().isEmpty())
        .flatMap(ignored -> repoModel.getPublicRepos(false, 1)
            .toObservable()
            .map(LoadTopRepoResult::success)
            .onErrorReturn(LoadTopRepoResult::failure)
            .subscribeOn(Schedulers.io())
            .startWith(LoadTopRepoResult.inProgress()));
  }
}
