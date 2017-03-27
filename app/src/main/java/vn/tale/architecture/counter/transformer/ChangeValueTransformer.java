package vn.tale.architecture.counter.transformer;

import io.reactivex.Observable;
import vn.tale.architecture.common.mvvm.Action;
import vn.tale.architecture.common.mvvm.Result;
import vn.tale.architecture.common.mvvm.Transformer;
import vn.tale.architecture.counter.CounterUiModel;
import vn.tale.architecture.counter.action.ChangeValueAction;
import vn.tale.architecture.counter.result.ChangeValueResult;

/**
 * Created by Giang Nguyen on 3/24/17.
 */

public class ChangeValueTransformer implements Transformer<CounterUiModel> {

  @Override
  public Observable<Result> transform(Observable<Action> action$, CounterUiModel counterUiModel) {
    return action$
        .ofType(ChangeValueAction.class)
        .map(action -> {
          if (action == ChangeValueAction.INCREASE) {
            return ChangeValueResult.make(counterUiModel.value() + 1);
          } else {
            return ChangeValueResult.make(counterUiModel.value() - 1);
          }
        });
  }
}
