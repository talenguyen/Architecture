package vn.tale.architecture.counter;

import com.google.auto.value.AutoValue;

/**
 * Created by Giang Nguyen on 3/24/17.
 */
@AutoValue
public abstract class CounterUiModel {

  public abstract int value();

  public static CounterUiModel make(int value) {
    return new AutoValue_CounterUiModel(value);
  }
}
