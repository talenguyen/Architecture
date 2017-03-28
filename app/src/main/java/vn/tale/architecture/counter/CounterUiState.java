package vn.tale.architecture.counter;

import com.google.auto.value.AutoValue;

/**
 * Created by Giang Nguyen on 3/24/17.
 */
@AutoValue
public abstract class CounterUiState {

  public static CounterUiState make(int value) {
    return new AutoValue_CounterUiState(value);
  }

  public abstract int value();
}
