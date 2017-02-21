package vn.tale.architecture.repos.menu.top;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import vn.tale.architecture.common.base.MvpPresenter;
import vn.tale.architecture.model.User;
import vn.tale.architecture.model.manager.UserModel;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class TopMenuPresenter extends MvpPresenter<TopMenuView> {

  private final UserModel userModel;

  public TopMenuPresenter(UserModel userModel) {
    this.userModel = userModel;
  }

  @Override protected void onViewAttached() {
    super.onViewAttached();
    Disposable disposable = userModel.user()
        .subscribe(new Consumer<User>() {
          @Override public void accept(User user) throws Exception {
            final TopMenuView view = getView();
            if (view == null) {
              return;
            }
            if (user.equals(UserModel.ANNOYMOUS)) {
              view.showLoginMenu();
            } else {
              view.showLogoutMenu();
            }
          }
        });
    disposeOnDetach(disposable);
  }
}
