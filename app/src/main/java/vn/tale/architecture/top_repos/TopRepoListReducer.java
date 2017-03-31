package vn.tale.architecture.top_repos;

import ix.Ix;
import java.util.List;
import vn.tale.architecture.common.reduxer.Reducer;
import vn.tale.architecture.common.reduxer.Result;
import vn.tale.architecture.model.Repo;
import vn.tale.architecture.top_repos.result.LoadMoreResult;
import vn.tale.architecture.top_repos.result.LoadResult;
import vn.tale.architecture.top_repos.result.RefreshResult;

/**
 * Created by Giang Nguyen on 3/28/17.
 */

public class TopRepoListReducer implements Reducer<TopRepoListState> {

  @SuppressWarnings("ThrowableResultOfMethodCallIgnored") @Override
  public TopRepoListState apply(TopRepoListState state, Result result)
      throws Exception {
    if (result instanceof LoadResult) {
      final LoadResult topRepoResult = (LoadResult) result;
      if (topRepoResult.loading()) {
        return TopRepoListState.builder(state)
            .loading(true)
            .make();
      } else if (topRepoResult.error() != null) {
        return TopRepoListState.builder(state)
            .loading(false)
            .loadError(topRepoResult.error())
            .make();
      } else {
        return TopRepoListState.builder(state)
            .content(topRepoResult.content())
            .loading(false)
            .make();
      }
    } else if (result instanceof RefreshResult) {
      final RefreshResult refreshResult = (RefreshResult) result;
      if (refreshResult.loading()) {
        return TopRepoListState.builder(state)
            .refreshing(true)
            .make();
      } else if (refreshResult.error() != null) {
        return TopRepoListState.builder(state)
            .refreshing(false)
            .refreshError(refreshResult.error())
            .make();
      } else {
        return TopRepoListState.builder(state)
            .refreshing(false)
            .content(refreshResult.content())
            .make();
      }
    } else if (result instanceof LoadMoreResult) {
      final LoadMoreResult loadMoreResult = (LoadMoreResult) result;
      if (loadMoreResult.loading()) {
        return TopRepoListState.builder(state)
            .loadingMore(true)
            .make();
      } else if (loadMoreResult.error() != null) {
        return TopRepoListState.builder(state)
            .loadingMore(false)
            .loadMoreError(loadMoreResult.error())
            .make();
      } else {
        final List<Repo> repoList = Ix.from(state.content())
            .mergeWith(((LoadMoreResult) result).content())
            .toList();
        return TopRepoListState.builder(state)
            .loadingMore(false)
            .page(((LoadMoreResult) result).page())
            .content(repoList)
            .make();
      }
    }
    throw new IllegalArgumentException("Unknown result");
  }
}
