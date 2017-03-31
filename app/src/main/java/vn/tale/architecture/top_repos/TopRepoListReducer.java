package vn.tale.architecture.top_repos;

import ix.Ix;
import java.util.List;
import vn.tale.architecture.common.mvvm.Reducer;
import vn.tale.architecture.common.mvvm.Result;
import vn.tale.architecture.model.Repo;
import vn.tale.architecture.top_repos.result.LoadMoreResult;
import vn.tale.architecture.top_repos.result.LoadTopRepoResult;
import vn.tale.architecture.top_repos.result.RefreshTopRepoResult;

/**
 * Created by Giang Nguyen on 3/28/17.
 */

public class TopRepoListReducer implements Reducer<TopRepoListUiState> {

  @SuppressWarnings("ThrowableResultOfMethodCallIgnored") @Override
  public TopRepoListUiState apply(TopRepoListUiState state, Result result)
      throws Exception {
    if (result instanceof LoadTopRepoResult) {
      final LoadTopRepoResult topRepoResult = (LoadTopRepoResult) result;
      if (topRepoResult.loading()) {
        return TopRepoListUiState.builder(state)
            .loading(true)
            .make();
      } else if (topRepoResult.error() != null) {
        return TopRepoListUiState.builder(state)
            .loading(false)
            .loadError(topRepoResult.error())
            .make();
      } else {
        return TopRepoListUiState.builder(state)
            .content(topRepoResult.content())
            .loading(false)
            .make();
      }
    } else if (result instanceof RefreshTopRepoResult) {
      final RefreshTopRepoResult refreshTopRepoResult = (RefreshTopRepoResult) result;
      if (refreshTopRepoResult.loading()) {
        return TopRepoListUiState.builder(state)
            .refreshing(true)
            .make();
      } else if (refreshTopRepoResult.error() != null) {
        return TopRepoListUiState.builder(state)
            .refreshing(false)
            .refreshError(refreshTopRepoResult.error())
            .make();
      } else {
        return TopRepoListUiState.builder(state)
            .refreshing(false)
            .content(refreshTopRepoResult.content())
            .make();
      }
    } else if (result instanceof LoadMoreResult) {
      final LoadMoreResult loadMoreResult = (LoadMoreResult) result;
      if (loadMoreResult.loading()) {
        return TopRepoListUiState.builder(state)
            .loadingMore(true)
            .make();
      } else if (loadMoreResult.error() != null) {
        return TopRepoListUiState.builder(state)
            .loadingMore(false)
            .loadMoreError(loadMoreResult.error())
            .make();
      } else {
        final List<Repo> repoList = Ix.from(state.content())
            .mergeWith(((LoadMoreResult) result).content())
            .toList();
        return TopRepoListUiState.builder(state)
            .loadingMore(false)
            .page(((LoadMoreResult) result).page())
            .content(repoList)
            .make();
      }
    }
    throw new IllegalArgumentException("Unknown result");
  }
}
