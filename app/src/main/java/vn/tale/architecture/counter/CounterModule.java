package vn.tale.architecture.counter;

import dagger.Module;
import dagger.Provides;
import vn.tale.architecture.common.mvvm.ViewModel;
import vn.tale.architecture.counter.transformer.ChangeValueTransformer;

/**
 * Created by Giang Nguyen on 3/24/17.
 */
@Module
public class CounterModule {

  @Provides ViewModel<CounterUiModel> provideCounterUiModelViewModel() {
    return ViewModel.<CounterUiModel>builder()
        .initialState(CounterUiModel.make(0))
        .transformers(new ChangeValueTransformer())
        .reducer(new CounterReducer())
        .make();
  }
}
