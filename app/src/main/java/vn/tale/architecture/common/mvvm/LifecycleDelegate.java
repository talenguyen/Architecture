package vn.tale.architecture.common.mvvm;

/**
 * Created by Giang Nguyen on 3/23/17.
 */

public class LifecycleDelegate<UiState> {

  private final ViewModel<UiState> viewModel;

  public LifecycleDelegate(ViewModel<UiState> viewModel) {
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
