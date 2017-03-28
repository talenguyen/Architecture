package vn.tale.architecture.top_repos;

import vn.tale.architecture.common.mvvm.Reducer;
import vn.tale.architecture.common.mvvm.Result;
import vn.tale.architecture.top_repos.result.LoadTopRepoResult;

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
        return TopRepoListUiState.builder()
            .loading(true)
            .make();
      } else if (topRepoResult.error() != null) {
        return TopRepoListUiState.builder()
            .error(topRepoResult.error())
            .make();
      } else {
        return TopRepoListUiState.builder()
            .content(topRepoResult.content())
            .make();
      }
    }
    throw new IllegalArgumentException("Unknown result");
  }
}
