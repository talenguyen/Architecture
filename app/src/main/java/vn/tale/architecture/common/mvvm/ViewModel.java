package vn.tale.architecture.common.mvvm;

import android.support.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Giang Nguyen on 3/23/17.
 */
public class ViewModel<UiState> {

  private final UiState initialState;
  private final BiFunction<UiState, Result, UiState> reducer;
  private final ObservableTransformer<Action, Result>[] transformers;
  private final PublishSubject<Action> action$;
  private final BehaviorSubject<UiState> state$;
  private Disposable disposable;

  ViewModel(@NonNull UiState initialState,
      @NonNull BiFunction<UiState, Result, UiState> reducer,
      @NonNull ObservableTransformer<Action, Result>[] transformers) {
    this.initialState = initialState;
    this.reducer = reducer;
    this.transformers = transformers;
    this.action$ = PublishSubject.create();
    this.state$ = BehaviorSubject.create();
  }

  public Observable<UiState> state$() {
    return state$;
  }

  public void dispatch(Action action) {
    action$.onNext(action);
  }

  public void startBinding() {
    disposable = action$
        .publish(shared -> Observable.fromArray(transformers)
            .flatMap(shared::compose))
        .scan(initialState, reducer)
        .subscribe(state$::onNext);
  }

  public void stopBinding() {
    if (disposable != null) {
      disposable.dispose();
    }
  }
}
