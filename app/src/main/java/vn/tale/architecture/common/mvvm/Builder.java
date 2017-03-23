package vn.tale.architecture.common.mvvm;

import android.support.annotation.NonNull;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.BiFunction;

public class Builder<UiState> {
  private UiState initialState;
  private BiFunction<UiState, Result, UiState> reducer;
  private ObservableTransformer<Action, Result>[] transformers;

  public Builder<UiState> initialState(@NonNull UiState initialState) {
    this.initialState = initialState;
    return this;
  }

  public Builder<UiState> reducer(@NonNull BiFunction<UiState, Result, UiState> reducer) {
    this.reducer = reducer;
    return this;
  }

  @SafeVarargs
  public final Builder<UiState> transformers(
      @NonNull ObservableTransformer<Action, Result>... transformers) {
    if (transformers.length == 0) {
      throw new IllegalArgumentException("transformers must not be empty");
    }
    this.transformers = transformers;
    return this;
  }

  public ViewModel<UiState> make() {
    return new ViewModel<>(initialState, reducer, transformers);
  }
}