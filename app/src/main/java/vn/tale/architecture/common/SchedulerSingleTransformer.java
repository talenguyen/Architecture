package vn.tale.architecture.common;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Giang Nguyen on 2/26/17.
 */

public interface SchedulerSingleTransformer {

  SchedulerSingleTransformer IO = new SchedulerSingleTransformer() {
    @SuppressWarnings("unchecked")
    @Override public <T> SingleTransformer<T, T> transformer() {
      return (SingleTransformer<T, T>) new SingleTransformer() {
        @Override public SingleSource apply(Single upstream) {
          return upstream.subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread());
        }
      };
    }
  };

  SchedulerSingleTransformer TEST = new SchedulerSingleTransformer() {
    @SuppressWarnings("unchecked")
    @Override public <T> SingleTransformer<T, T> transformer() {
      return (SingleTransformer<T, T>) new SingleTransformer() {
        @Override public SingleSource apply(Single upstream) {
          return upstream.subscribeOn(Schedulers.trampoline())
              .observeOn(Schedulers.trampoline());
        }
      };
    }
  };

  <T> SingleTransformer<T, T> transformer();
}
