package vn.tale.architecture;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.tale.architecture.common.EmailValidator;
import vn.tale.architecture.common.SchedulerObservableTransformer;
import vn.tale.architecture.common.SchedulerSingleTransformer;

/**
 * Created by Giang Nguyen on 2/27/17.
 */
@Module
public class AppModule {

  @Provides
  public EmailValidator emailValidator() {
    return new EmailValidator();
  }

  @Provides
  SchedulerObservableTransformer provideSchedulerObservableTransformer() {
    return new SchedulerObservableTransformer() {
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
  }

  @Provides
  SchedulerSingleTransformer provideSchedulerSingleTransformer() {
    return new SchedulerSingleTransformer() {
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
  }
}
