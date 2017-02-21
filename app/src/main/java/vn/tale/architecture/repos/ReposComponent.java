package vn.tale.architecture.repos;

import vn.tale.architecture.AppComponent;
import vn.tale.architecture.repos.menu.bottom.BottomMenuPresenter;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class ReposComponent {

  private final AppComponent appComponent;

  public ReposComponent(AppComponent appComponent) {
    this.appComponent = appComponent;
  }

  BottomMenuPresenter provideBottomMenuPresenter() {
    return new BottomMenuPresenter(appComponent.provideUserModel());
  }
}
