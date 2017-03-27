package vn.tale.architecture.counter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import javax.inject.Inject;
import vn.tale.architecture.App;
import vn.tale.architecture.R;
import vn.tale.architecture.common.base.MvvmActivity;
import vn.tale.architecture.common.dagger.DaggerComponentFactory;
import vn.tale.architecture.common.mvvm.ViewModel;
import vn.tale.architecture.counter.action.ChangeValueAction;

/**
 * Created by Giang Nguyen on 3/24/17.
 */

public class CounterActivity extends MvvmActivity<CounterComponent, CounterUiModel> {

  @Inject ViewModel<CounterUiModel> viewModel;
  @BindView(R.id.tvValue) TextView tvValue;

  @Override protected void injectDependencies() {
    daggerComponent().inject(this);
  }

  @Override protected ViewModel<CounterUiModel> viewModel() {
    return viewModel;
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
    dispatch(ChangeValueAction.INCREASE);
  }

  @OnClick(R.id.decrease) public void onDecreaseClick() {
    dispatch(ChangeValueAction.DECREASE);
  }

  @Override protected void render(CounterUiModel model) {
    runOnUiThread(() -> tvValue.setText(String.valueOf(model.value())));
  }
}
