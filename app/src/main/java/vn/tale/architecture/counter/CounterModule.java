package vn.tale.architecture.counter;

import dagger.Module;
import dagger.Provides;
import vn.tale.architecture.common.reduxer.Store;
import vn.tale.architecture.counter.transformer.ChangeValueTransformer;

/**
 * Created by Giang Nguyen on 3/24/17.
 */
@Module
public class CounterModule {

  @Provides Store<CounterUiState> provideCounterUiModelViewModel() {
    return Store.<CounterUiState>builder()
        .initialState(CounterUiState.make(0))
        .transformers(new ChangeValueTransformer())
        .reducer(new CounterReducer())
        .make();
  }
}
