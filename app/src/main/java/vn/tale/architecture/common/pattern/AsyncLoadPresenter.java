package vn.tale.architecture.common.pattern;

import android.support.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import vn.tale.architecture.common.base.MvpPresenter;

public class AsyncLoadPresenter<T> extends MvpPresenter<AsyncLoad.View<T>> {

    private final AsyncLoad.GetDataInteractor<T> getDataInteractor;
    private final Scheduler threadScheduler;
    private final Scheduler uiScheduler;
    private final boolean cache;
    private T data;
    private Observable<T> getData;
    private boolean error;

    public AsyncLoadPresenter(AsyncLoad.GetDataInteractor<T> getDataInteractor, Scheduler threadScheduler,
        Scheduler uiScheduler, boolean cache) {
      this.getDataInteractor = getDataInteractor;
      this.threadScheduler = threadScheduler;
      this.uiScheduler = uiScheduler;
      this.cache = cache;
    }

    @Override protected void onViewAttached() {
      super.onViewAttached();
      final AsyncLoad.View<T> view = getView();

      disposeOnDetach(view.onLoad().subscribe(ignored -> load()));
      disposeOnDetach(view.onDestroy().subscribe(ignored -> detachView()));
    }

    private void load() {
      AsyncLoad.View<T> view = getView();
      if (cache && data != null) {
        view.showContent(data);
        return;
      }
      if (getData == null || error) {
        getData = cache ? getDataInteractor.getData().cache() : getDataInteractor.getData();
        error = false;
      }
      view.showLoading();
      disposeOnDetach(getData
          .subscribeOn(threadScheduler)
          .observeOn(uiScheduler)
          .subscribe(t -> {
            data = t;
            getView().showContent(t);
          }, throwable -> {
            error = true;
            getView().showError(throwable);
          }));
    }

    @NonNull @Override protected AsyncLoad.View<T> getView() {
      final AsyncLoad.View<T> view = super.getView();
      if (view == null) {
        return new NoOpView<>();
      }
      return view;
    }
  }