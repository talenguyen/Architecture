package vn.tale.architecture.counter.transformer;

import io.reactivex.Observable;
import vn.tale.architecture.common.mvvm.Action;
import vn.tale.architecture.common.mvvm.Function0;
import vn.tale.architecture.common.mvvm.Result;
import vn.tale.architecture.common.mvvm.Transformer;
import vn.tale.architecture.counter.CounterUiState;
import vn.tale.architecture.counter.action.ChangeValueAction;
import vn.tale.architecture.counter.result.ChangeValueResult;

/**
 * Created by Giang Nguyen on 3/24/17.
 */

public class ChangeValueTransformer implements Transformer<CounterUiState> {

  @Override public Observable<Result> transform(Observable<Action> action$,
      Function0<CounterUiState> getState) {
    return action$
        .ofType(ChangeValueAction.class)
        .map(action -> {
          final CounterUiState uiModel = getState.apply();
          if (action == ChangeValueAction.INCREASE) {
            return ChangeValueResult.make(uiModel.value() + 1);
          } else {
            return ChangeValueResult.make(uiModel.value() - 1);
          }
        });
  }
}
