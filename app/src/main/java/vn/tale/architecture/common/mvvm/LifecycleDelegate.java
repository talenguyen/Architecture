package vn.tale.architecture.common.mvvm;

/**
 * Created by Giang Nguyen on 3/23/17.
 */

public class LifecycleDelegate<UiModel> {

  private final ViewModel<UiModel> viewModel;

  public LifecycleDelegate(ViewModel<UiModel> viewModel) {
    this.viewModel = viewModel;
  }

  public void onStart() {
    viewModel.startBinding();
  }

  public void onStop(boolean finishing) {
    if (finishing) {
      viewModel.stopBinding();
    }
  }
}
