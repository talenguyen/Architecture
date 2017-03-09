package vn.tale.architecture.common.pattern;

import io.reactivex.Observable;
import java.util.List;

class NoOpPagingView<T> implements AsyncLoadPaging.View<T> {

  @Override public Observable<Object> onLoad() {
    return Observable.empty();
  }

  @Override public Observable<Object> onRefresh() {
    return Observable.empty();
  }

  @Override public Observable<Object> onLoadMore() {
    return Observable.empty();
  }

  @Override public Observable<Object> onDestroy() {
    return Observable.empty();
  }

  @Override public void showLoading() {
    // No-Op
  }

  @Override public void showLoadMoreIndicator() {
    // No-Op
  }

  @Override public void showRefreshIndicator() {
    // No-Op
  }

  @Override public void showContent(List<T> content) {
    // No-Op
  }

  @Override public void showError(Throwable error) {
    // No-Op
  }

  @Override public void showLoadMoreError(Throwable error) {
    // No-Op
  }

  @Override public void showRefreshError(Throwable error) {
    // No-Op
  }
}