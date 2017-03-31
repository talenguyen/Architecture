package vn.tale.architecture.common.reduxer;

/**
 * Created by Giang Nguyen on 3/23/17.
 */

public class LifecycleDelegate<UiState> {

  private final Store<UiState> store;

  public LifecycleDelegate(Store<UiState> store) {
    this.store = store;
  }

  public void onStart() {
    store.startBinding();
  }

  public void onStop(boolean finishing) {
    if (finishing) {
      store.stopBinding();
    }
  }
}
