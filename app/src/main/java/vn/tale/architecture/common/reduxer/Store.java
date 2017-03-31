package vn.tale.architecture.common.reduxer;

import android.support.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import vn.tale.architecture.common.Preconditions;

/**
 * Created by Giang Nguyen on 3/23/17.
 */
public class Store<UiState> {

  private final Reducer<UiState> reducer;
  private final PublishSubject<Action> action$;
  private final BehaviorSubject<UiState> state$;
  private final Observable<Result> result$;
  private Disposable disposable;

  Store(@NonNull UiState initialState,
      @NonNull Reducer<UiState> reducer,
      @NonNull Transformer<UiState>[] transformers) {
    this.reducer = reducer;
    this.action$ = PublishSubject.create();
    this.state$ = BehaviorSubject.createDefault(initialState);
    this.result$ = Observable.fromArray(transformers)
        .flatMap(transformer -> transformer.transform(action$, this::currentState));
  }

  public static <UiState> Builder<UiState> builder() {
    return new Builder<>();
  }

  private UiState currentState() {
    return state$.getValue();
  }

  public Observable<UiState> state$() {
    return state$;
  }

  public void dispatch(Action action) {
    action$.onNext(action);
  }

  void startBinding() {
    if (disposable != null) {
      // binding is started already
      return;
    }
    disposable = result$
        .scan(currentState(), reducer)
        .subscribe(state$::onNext);
  }

  void stopBinding() {
    if (disposable != null) {
      disposable.dispose();
      disposable = null;
    }
  }

  public static class Builder<UiState> {
    private UiState initialState;
    private Reducer<UiState> reducer;
    private Transformer<UiState>[] transformers;

    Builder() {
      // private constructor
    }

    public Builder<UiState> initialState(@NonNull UiState initialState) {
      Preconditions.checkNotNull(initialState, "initialState must not be null");
      this.initialState = initialState;
      return this;
    }

    public Builder<UiState> reducer(@NonNull Reducer<UiState> reducer) {
      Preconditions.checkNotNull(reducer, "reducer must not be null");
      this.reducer = reducer;
      return this;
    }

    @SafeVarargs
    public final Builder<UiState> transformers(@NonNull Transformer<UiState>... transformers) {
      Preconditions.checkNotNull(transformers);
      Preconditions.checkNotEmpty(transformers, "transformers must not be empty");
      this.transformers = transformers;
      return this;
    }

    public Store<UiState> make() {
      return new Store<>(initialState, reducer, transformers);
    }
  }
}
