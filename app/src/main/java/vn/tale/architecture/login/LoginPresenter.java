package vn.tale.architecture.login;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import vn.tale.architecture.common.EmailValidator;
import vn.tale.architecture.common.SchedulerSingleTransformer;
import vn.tale.architecture.common.mvp.MvpPresenter;
import vn.tale.architecture.model.error.InvalidEmailError;
import vn.tale.architecture.model.manager.UserModel;

import static vn.tale.architecture.login.LoginViewState.loginState;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

class LoginPresenter extends MvpPresenter<LoginViewState, LoginView> {
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
    validateInput(getView());

    handleSubmitLogin(getView());
  }

  private void handleSubmitLogin(LoginView view) {

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
    disposeOnStop(userModel.login(email, password)
        .compose(schedulerSingleTransformer.transformer())
        .subscribe(user -> {
          if (getView() != null) {
            getView().render(loginState(true, null));
          }
        }, throwable -> {
          if (getView() != null) {
            getView().render(loginState(false, throwable));
          }
        }));
  }

  private void validateInput(LoginView view) {
    disposeOnDetach(view.emailChanges().skip(1)
        .map(emailValidator::isValid)
        .subscribe(validInput -> {
          final LoginView view1 = getView();
          if (view1 == null) {
            return;
          }
          if (validInput) {
            view1.render(loginState(false, null));
          } else {
            view1.render(loginState(false, new InvalidEmailError()));
          }
        }));
  }
}
