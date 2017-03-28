package vn.tale.architecture.common.mvvm;

import io.reactivex.Observable;

/**
 * Created by Giang Nguyen on 3/24/17.
 */

public interface Transformer<UiState> {

  Observable<Result> transform(Observable<Action> action$, Function0<UiState> getState);
}
