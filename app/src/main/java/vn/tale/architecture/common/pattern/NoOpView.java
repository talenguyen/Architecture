package vn.tale.architecture.common.pattern;

import io.reactivex.Observable;

/**
 * Created by Giang Nguyen on 3/8/17.
 */

class NoOpView<T> implements AsyncLoad.View<T> {

  @Override public Observable<Object> onLoad() {
    return Observable.empty();
  }

  @Override public Observable<Object> onDestroy() {
    return Observable.empty();
  }

  @Override public void showLoading() {
    // No-Op
  }

  @Override public void showContent(T content) {
    // No-Op
  }

  @Override public void showError(Throwable error) {
    // No-Op
  }
}
