package vn.tale.architecture.common.mvvm;

import android.support.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import vn.tale.architecture.common.Preconditions;

/**
 * Created by Giang Nguyen on 3/23/17.
 */
public class ViewModel<UiModel> {

  private final UiModel initialState;
  private final Reducer<UiModel> reducer;
  private final Transformer<UiModel>[] transformers;
  private final PublishSubject<Action> action$;
  private final BehaviorSubject<UiModel> state$;
  private Disposable disposable;

  ViewModel(@NonNull UiModel initialState,
      @NonNull Reducer<UiModel> reducer,
      @NonNull Transformer<UiModel>[] transformers) {
    this.initialState = initialState;
    this.reducer = reducer;
    this.transformers = transformers;
    this.action$ = PublishSubject.create();
    this.state$ = BehaviorSubject.create();
  }

  public static <UiModel> Builder<UiModel> builder() {
    return new Builder<>();
  }

  public UiModel currentState() {
    if (state$.hasValue()) {
      return state$.getValue();
    }
    return initialState;
  }

  public Observable<UiModel> state$() {
    return state$;
  }

  public void dispatch(Action action) {
    action$.onNext(action);
  }

  public void startBinding() {
    final Observable<Result> result$ = action$
        .publish(shared -> Observable.fromArray(transformers)
            .flatMap(transformer -> transformer.transform(shared, currentState())));

    disposable = result$
        .scan(initialState, reducer)
        .subscribe(state$::onNext);
  }

  public void stopBinding() {
    if (disposable != null) {
      disposable.dispose();
    }
  }

  public static class Builder<UiModel> {
    private UiModel initialState;
    private Reducer<UiModel> reducer;
    private Transformer<UiModel>[] transformers;

    Builder() {
    }

    public Builder<UiModel> initialState(@NonNull UiModel initialState) {
      Preconditions.checkNotNull(initialState, "initialState must not be null");
      this.initialState = initialState;
      return this;
    }

    public Builder<UiModel> reducer(@NonNull Reducer<UiModel> reducer) {
      Preconditions.checkNotNull(reducer, "reducer must not be null");
      this.reducer = reducer;
      return this;
    }

    @SafeVarargs
    public final Builder<UiModel> transformers(@NonNull Transformer<UiModel>... transformers) {
      Preconditions.checkNotNull(transformers);
      Preconditions.checkNotEmpty(transformers, "transformers must not be empty");
      this.transformers = transformers;
      return this;
    }

    public ViewModel<UiModel> make() {
      return new ViewModel<>(initialState, reducer, transformers);
    }
  }
}
