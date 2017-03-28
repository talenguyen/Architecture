package vn.tale.architecture.counter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Action;
import javax.inject.Inject;
import vn.tale.architecture.App;
import vn.tale.architecture.R;
import vn.tale.architecture.common.base.ReduxActivity;
import vn.tale.architecture.common.dagger.DaggerComponentFactory;
import vn.tale.architecture.common.mvvm.Store;
import vn.tale.architecture.counter.action.ChangeValueAction;

/**
 * Created by Giang Nguyen on 3/24/17.
 */

public class CounterActivity extends ReduxActivity<CounterComponent, CounterUiState> {

  @Inject Store<CounterUiState> store;
  @BindView(R.id.tvValue) TextView tvValue;

  @Override protected void injectDependencies() {
    daggerComponent().inject(this);
  }

  @Override protected Store<CounterUiState> store() {
    return store;
  }

  @Override protected DaggerComponentFactory<CounterComponent> daggerComponentFactory() {
    return () -> App.get(this).getAppComponent().plus(new CounterModule());
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_counter);
    bindViews(this);
  }

  @OnClick(R.id.increase) public void onIncreaseClick() {
    store.dispatch(ChangeValueAction.INCREASE);
  }

  @OnClick(R.id.decrease) public void onDecreaseClick() {
    store.dispatch(ChangeValueAction.DECREASE);
  }

  @NonNull @Override protected Action render(CounterUiState state) {
    return () -> tvValue.setText(String.valueOf(state.value()));
  }
}
