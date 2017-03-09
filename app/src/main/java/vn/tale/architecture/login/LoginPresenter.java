package vn.tale.architecture.login;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import vn.tale.architecture.common.EmailValidator;
import vn.tale.architecture.common.SchedulerSingleTransformer;
import vn.tale.architecture.common.base.MvpPresenter;
import vn.tale.architecture.model.manager.UserModel;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

class LoginPresenter extends MvpPresenter<LoginView> {
  private final UserModel userModel;
  private final EmailValidator emailValidator;
  private final SchedulerSingleTransformer schedulerSingleTransformer;

  LoginPresenter(UserModel userModel, EmailValidator emailValidator,
      SchedulerSingleTransformer schedulerSingleTransformer) {
    this.userModel = userModel;
    this.emailValidator = emailValidator;
    this.schedulerSingleTransformer = schedulerSingleTransformer;
  }

  @Override protected void onViewAttached() {
    super.onViewAttached();
    final LoginView view = getView();
    if (view == null) {
      return;
    }

    validateInput(view);

    handleSubmitLogin();
  }

  private void handleSubmitLogin() {
    final LoginView view = getView();
    if (view == null) {
      return;
    }

    final Observable<String> email = view.emailChanges().map(CharSequence::toString);

    final Observable<String> password = view.passwordChanges().map(CharSequence::toString);

    disposeOnDetach(view.signInClick()
        .flatMap(new Function<Object, ObservableSource<String[]>>() {
          @Override public ObservableSource<String[]> apply(Object object)
              throws Exception {
            return Observable.zip(
                email,
                password,
                (email1, password1) -> new String[] { email1, password1 });
          }
        })
        .subscribe(credential -> signIn(credential[0], credential[1])));
  }

  private void signIn(String email, String password) {
    disposeOnDetach(userModel.login(email, password)
        .compose(schedulerSingleTransformer.transformer())
        .subscribe(user -> {
          if (getView() != null) {
            getView().showSuccessMessage();
            getView().hide();
          }
        }, throwable -> {
          if (getView() != null) {
            getView().showLoginFailMessage();
          }
        }));
  }

  private void validateInput(LoginView view) {
    view.disableSignInButton();
    disposeOnDetach(view.emailChanges().skip(1)
        .map(emailValidator::isValid)
        .subscribe(validInput -> {
          final LoginView view1 = getView();
          if (view1 == null) {
            return;
          }
          if (validInput) {
            view1.hideEmailError();
            view1.enableSignInButton();
          } else {
            view1.showInvalidEmailError();
            view1.disableSignInButton();
          }
        }));
  }
}
