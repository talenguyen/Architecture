package vn.tale.architecture.top_repos;

import vn.tale.architecture.common.mvvm.Reducer;
import vn.tale.architecture.common.mvvm.Result;
import vn.tale.architecture.top_repos.result.LoadTopRepoResult;
import vn.tale.architecture.top_repos.result.RefreshTopRepoResult;

/**
 * Created by Giang Nguyen on 3/28/17.
 */

public class TopRepoListReducer implements Reducer<TopRepoListUiState> {

  @SuppressWarnings("ThrowableResultOfMethodCallIgnored") @Override
  public TopRepoListUiState apply(TopRepoListUiState topRepoListUiState, Result result)
      throws Exception {
    if (result instanceof LoadTopRepoResult) {
      final LoadTopRepoResult topRepoResult = (LoadTopRepoResult) result;
      if (topRepoResult.loading()) {
        return TopRepoListUiState.builder(topRepoListUiState)
            .loading(true)
            .make();
      } else if (topRepoResult.error() != null) {
        return TopRepoListUiState.builder(topRepoListUiState)
            .loading(false)
            .loadError(topRepoResult.error())
            .make();
      } else {
        return TopRepoListUiState.builder(topRepoListUiState)
            .content(topRepoResult.content())
            .loading(false)
            .make();
      }
    } else if (result instanceof RefreshTopRepoResult) {
      final RefreshTopRepoResult refreshTopRepoResult = (RefreshTopRepoResult) result;
      if (refreshTopRepoResult.loading()) {
        return TopRepoListUiState.builder(topRepoListUiState)
            .refreshing(true)
            .make();
      } else if (refreshTopRepoResult.error() != null) {
        return TopRepoListUiState.builder(topRepoListUiState)
            .refreshing(false)
            .refreshError(refreshTopRepoResult.error())
            .make();
      } else {
        return TopRepoListUiState.builder(topRepoListUiState)
            .refreshing(false)
            .content(refreshTopRepoResult.content())
            .make();
      }
    }
    throw new IllegalArgumentException("Unknown result");
  }
}
