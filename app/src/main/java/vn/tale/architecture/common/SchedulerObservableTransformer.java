package vn.tale.architecture.common;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Giang Nguyen on 2/26/17.
 */

public interface SchedulerObservableTransformer {

  SchedulerObservableTransformer IO = new SchedulerObservableTransformer() {
    @SuppressWarnings("unchecked")
    @Override public <T> ObservableTransformer<T, T> transformer() {
      return (ObservableTransformer<T, T>) new ObservableTransformer() {
        @Override public ObservableSource apply(Observable upstream) {
          return upstream.subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread());
        }
      };
    }
  };

  SchedulerObservableTransformer TEST = new SchedulerObservableTransformer() {
    @SuppressWarnings("unchecked")
    @Override public <T> ObservableTransformer<T, T> transformer() {
      return (ObservableTransformer<T, T>) new ObservableTransformer() {
        @Override public ObservableSource apply(Observable upstream) {
          return upstream.subscribeOn(Schedulers.trampoline())
              .observeOn(Schedulers.trampoline());
        }
      };
    }
  };

  <T> ObservableTransformer<T, T> transformer();
}
