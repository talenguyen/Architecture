package vn.tale.architecture.login;

import io.reactivex.Observable;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

interface LoginView {

  Observable<CharSequence> emailChanges();

  Observable<CharSequence> passwordChanges();

  Observable<Object> signInClick();

  void showInvalidEmailError();

  void hideEmailError();

  void showLoginFailMessage();

  void showSuccessMessage();

  void disableSignInButton();

  void enableSignInButton();

  void hide();
}
