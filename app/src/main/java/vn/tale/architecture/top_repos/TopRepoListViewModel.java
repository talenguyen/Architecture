package vn.tale.architecture.top_repos;

import io.reactivex.Observable;
import java.util.List;
import vn.tale.architecture.model.Repo;

/**
 * Created by Giang Nguyen on 3/31/17.
 */
public class TopRepoListViewModel {

  private final Observable<TopRepoListState> state$;

  public TopRepoListViewModel(Observable<TopRepoListState> state$) {
    this.state$ = state$;
  }

  public Observable<TopRepoListState> loading$() {
    return state$
        .filter(TopRepoListState::loading);
  }

  public Observable<TopRepoListState> refreshing$() {
    return state$
        .filter(TopRepoListState::refreshing);
  }

  public Observable<TopRepoListState> loadingMore$() {
    return state$
        .filter(TopRepoListState::loadingMore);
  }

  public Observable<Throwable> loadError$() {
    return state$
        .filter(state -> state.loadError() != null)
        .map(TopRepoListState::loadError);
  }

  public Observable<Throwable> refreshError$() {
    return state$
        .filter(state -> state.refreshError() != null)
        .map(TopRepoListState::refreshError);
  }

  public Observable<Throwable> loadMoreError$() {
    return state$
        .filter(state -> state.loadMoreError() != null)
        .map(TopRepoListState::loadMoreError);
  }

  public Observable<List<Repo>> content$() {
    return state$
        .filter(state -> !state.loading()
            && !state.refreshing()
            && state.loadError() == null
            && state.refreshError() == null)
        .map(TopRepoListState::content);
  }
}
