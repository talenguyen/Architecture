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
public class ViewModel<UiModel> {

  private final UiModel initialState;
  private final BiFunction<UiModel, Result, UiModel> reducer;
  private final ObservableTransformer<Action, Result>[] transformers;
  private final PublishSubject<Action> action$;
  private final BehaviorSubject<UiModel> state$;
  private Disposable disposable;

  ViewModel(@NonNull UiModel initialState,
      @NonNull BiFunction<UiModel, Result, UiModel> reducer,
      @NonNull ObservableTransformer<Action, Result>[] transformers) {
    this.initialState = initialState;
    this.reducer = reducer;
    this.transformers = transformers;
    this.action$ = PublishSubject.create();
    this.state$ = BehaviorSubject.create();
  }

  public Observable<UiModel> state$() {
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
