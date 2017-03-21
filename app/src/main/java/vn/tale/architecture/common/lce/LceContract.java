package vn.tale.architecture.common.lce;

import io.reactivex.Observable;
import io.reactivex.Single;
import vn.tale.architecture.common.mvp.MvpView;

/**
 * Created by Giang Nguyen on 3/20/17.
 */

public interface LceContract {

  interface View<T> extends MvpView<LceViewState<T>> {

    Observable<Object> loadIntent();
  }

  interface Model<T> {

    Single<T> getContent();
  }
}
