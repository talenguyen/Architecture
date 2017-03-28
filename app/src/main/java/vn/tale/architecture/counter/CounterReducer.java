package vn.tale.architecture.counter;

import vn.tale.architecture.common.mvvm.Reducer;
import vn.tale.architecture.common.mvvm.Result;
import vn.tale.architecture.counter.result.ChangeValueResult;

/**
 * Created by Giang Nguyen on 3/24/17.
 */

public class CounterReducer implements Reducer<CounterUiState> {

  @Override public CounterUiState apply(CounterUiState counterUiState, Result result)
      throws Exception {
    if (result instanceof ChangeValueResult) {
      return CounterUiState.make(((ChangeValueResult) result).value());
    }

    throw new IllegalArgumentException("Unknown result " + result);
  }
}
