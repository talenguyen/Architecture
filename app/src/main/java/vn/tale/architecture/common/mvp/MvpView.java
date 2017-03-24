package vn.tale.architecture.common.mvp;

/**
 * Created by Giang Nguyen on 3/20/17.
 */

public interface MvpView<UiModel> {

  void render(UiModel state);
}
