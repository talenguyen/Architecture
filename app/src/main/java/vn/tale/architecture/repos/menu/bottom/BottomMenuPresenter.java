package vn.tale.architecture.repos.menu.bottom;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import vn.tale.architecture.common.base.MvpPresenter;
import vn.tale.architecture.model.User;
import vn.tale.architecture.model.manager.UserModel;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class BottomMenuPresenter extends MvpPresenter<BottomMenuView> {

  private final UserModel userModel;

  public BottomMenuPresenter(UserModel userModel) {
    this.userModel = userModel;
  }

  @Override protected void onViewAttached() {
    super.onViewAttached();
    Disposable disposable = userModel.user()
        .subscribe(new Consumer<User>() {
          @Override public void accept(User user) throws Exception {
            final BottomMenuView view = getView();
            if (view == null) {
              return;
            }
            if (user.equals(UserModel.ANNOYMOUS)) {
              view.hideUserRepos();
            } else {
              view.showUserRepos();
            }
          }
        });
    disposeOnDetach(disposable);
  }
}
