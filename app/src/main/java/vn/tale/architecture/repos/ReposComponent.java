package vn.tale.architecture.repos;

import vn.tale.architecture.AppComponent;
import vn.tale.architecture.repos.menu.bottom.BottomMenuPresenter;
import vn.tale.architecture.repos.menu.top.TopMenuPresenter;
import vn.tale.architecture.repos.my.MyReposPresenter;
import vn.tale.architecture.repos.pub.PublicReposPresenter;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class ReposComponent {

  private final AppComponent appComponent;

  public ReposComponent(AppComponent appComponent) {
    this.appComponent = appComponent;
  }

  public BottomMenuPresenter provideBottomMenuPresenter() {
    return new BottomMenuPresenter(appComponent.provideUserModel());
  }

  public TopMenuPresenter provideTopMenuPresenter() {
    return new TopMenuPresenter(appComponent.provideUserModel());
  }

  public PublicReposPresenter providePublicReposPresenter() {
    return new PublicReposPresenter(appComponent.provideRepoModel());
  }

  public MyReposPresenter provideMyReposPresenter() {
    return new MyReposPresenter(appComponent.provideUserModel(), appComponent.provideRepoModel());
  }
}
