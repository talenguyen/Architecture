package vn.tale.architecture.common.pattern;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import java.lang.ref.WeakReference;

/**
 * Created by Giang Nguyen on 3/3/17.
 */

public interface AsyncLoad {

  interface View<T> {

    Observable<Object> onLoad();

    Observable<Object> onDestroy();

    void showLoading();

    void showContent(T content);

    void showError(Throwable error);
  }

  interface Model<T> {

    Observable<T> getData();
  }

  class AsyncLoadPresenter<T> {

    private final AsyncLoad.Model<T> model;
    private final Scheduler threadScheduler;
    private final Scheduler uiScheduler;
    private final boolean cache;
    private T data;
    private WeakReference<View<T>> viewRef;
    private CompositeDisposable compositeDisposable;
    private Observable<T> getData;

    public AsyncLoadPresenter(AsyncLoad.Model<T> model, Scheduler threadScheduler,
        Scheduler uiScheduler, boolean cache) {
      this.model = model;
      this.threadScheduler = threadScheduler;
      this.uiScheduler = uiScheduler;
      this.cache = cache;
    }

    public void attachView(@NonNull View<T> view) {
      viewRef = new WeakReference<>(view);
      compositeDisposable = new CompositeDisposable();

      compositeDisposable.add(view.onLoad()
          .subscribe(new Consumer<Object>() {
            @Override public void accept(Object object) throws Exception {
              load();
            }
          }));

      compositeDisposable.add(view.onDestroy()
          .subscribe(new Consumer<Object>() {
            @Override public void accept(Object object) throws Exception {
              detachView();
            }
          }));
    }

    private void load() {
      View<T> view = getView();
      if (view == null) {
        return;
      }
      if (cache && data != null) {
        view.showContent(data);
        return;
      }
      if (getData == null) {
        getData = cache ? model.getData().cache() : model.getData();
      }
      view.showLoading();
      compositeDisposable.add(getData
          .subscribeOn(threadScheduler)
          .observeOn(uiScheduler)
          .subscribe(new Consumer<T>() {
            @Override public void accept(T t) throws Exception {
              data = t;
              if (getView() != null) {
                getView().showContent(t);
              }
            }
          }, new Consumer<Throwable>() {
            @Override public void accept(Throwable throwable) throws Exception {
              if (getView() != null) {
                getView().showError(throwable);
              }
            }
          }));
    }

    @VisibleForTesting View<T> getView() {
      return viewRef == null ? null : viewRef.get();
    }

    private void detachView() {
      if (viewRef != null) {
        viewRef.clear();
      }
      if (compositeDisposable != null) {
        compositeDisposable.clear();
      }
    }
  }
}
