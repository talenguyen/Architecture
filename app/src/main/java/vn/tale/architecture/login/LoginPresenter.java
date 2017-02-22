package vn.tale.architecture.login;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import vn.tale.architecture.common.EmailValidator;
import vn.tale.architecture.common.base.MvpPresenter;
import vn.tale.architecture.model.User;
import vn.tale.architecture.model.manager.UserModel;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class LoginPresenter extends MvpPresenter<LoginView> {
  private final UserModel userModel;
  private final EmailValidator emailValidator;

  public LoginPresenter(UserModel userModel, EmailValidator emailValidator) {
    this.userModel = userModel;
    this.emailValidator = emailValidator;
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

    final Observable<String> email = view.emailChanges().map(new Function<CharSequence,
        String>() {
      @Override public String apply(CharSequence charSequence) throws Exception {
        return charSequence.toString();
      }
    });

    final Observable<String> password = view.passwordChanges().map(new Function<CharSequence,
        String>() {
      @Override public String apply(CharSequence charSequence) throws Exception {
        return charSequence.toString();
      }
    });

    disposeOnDetach(view.signInClick()
        .flatMap(new Function<Object, ObservableSource<String[]>>() {
          @Override public ObservableSource<String[]> apply(Object object)
              throws Exception {
            return Observable.zip(
                email,
                password,
                new BiFunction<String, String, String[]>() {
                  @Override public String[] apply(String email, String password)
                      throws Exception {
                    return new String[] { email, password };
                  }
                });
          }
        })
        .flatMapSingle(new Function<String[], Single<User>>() {
          @Override public Single<User> apply(String[] credential)
              throws Exception {
            return userModel.login(credential[0], credential[1]);
          }
        })
        .subscribe(new Consumer<User>() {
          @Override public void accept(User user) throws Exception {
            if (getView() != null) {
              getView().showSuccessMessage();
              getView().hide();
            }
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            if (getView() != null) {
              getView().showLoginFailMessage();
            }
          }
        }));
  }

  private void validateInput(LoginView view) {
    view.disableSignInButton();
    disposeOnDetach(view.emailChanges().skip(1)
        .map(new Function<CharSequence, Boolean>() {
          @Override public Boolean apply(CharSequence email) throws Exception {
            return emailValidator.isValid(email);
          }
        })
        .subscribe(new Consumer<Boolean>() {
          @Override public void accept(Boolean validInput) throws Exception {
            final LoginView view = getView();
            if (view == null) {
              return;
            }
            if (validInput) {
              view.hideEmailError();
              view.enableSignInButton();
            } else {
              view.showInvalidEmailError();
              view.disableSignInButton();
            }
          }
        }));
  }
}
