package vn.tale.architecture.common.mvvm;

import android.support.annotation.NonNull;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.BiFunction;

public class Builder<UiModel> {
  private UiModel initialState;
  private BiFunction<UiModel, Result, UiModel> reducer;
  private ObservableTransformer<Action, Result>[] transformers;

  public Builder<UiModel> initialState(@NonNull UiModel initialState) {
    this.initialState = initialState;
    return this;
  }

  public Builder<UiModel> reducer(@NonNull BiFunction<UiModel, Result, UiModel> reducer) {
    this.reducer = reducer;
    return this;
  }

  @SafeVarargs
  public final Builder<UiModel> transformers(
      @NonNull ObservableTransformer<Action, Result>... transformers) {
    if (transformers.length == 0) {
      throw new IllegalArgumentException("transformers must not be empty");
    }
    this.transformers = transformers;
    return this;
  }

  public ViewModel<UiModel> make() {
    return new ViewModel<>(initialState, reducer, transformers);
  }
}