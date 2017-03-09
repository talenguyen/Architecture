package vn.tale.architecture.common.pattern;

import io.reactivex.Observable;

/**
 * Created by Giang Nguyen on 3/3/17.
 */

public interface AsyncLoad {

  interface View<T> {

    Observable<Object> onLoad();

    Observable<Object> onDestroy();

    void showLoading();

    void showContent(T content);

    void showError(Throwable error);
  }

  interface GetDataInteractor<T> {

    Observable<T> getData();
  }
}
