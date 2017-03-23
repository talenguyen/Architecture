package vn.tale.architecture.common.mvp;

/**
 * Created by Giang Nguyen on 3/20/17.
 */

public interface MvpView<UiState> {

  void render(UiState state);
}
