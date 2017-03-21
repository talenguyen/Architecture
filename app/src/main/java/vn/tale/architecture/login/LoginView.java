package vn.tale.architecture.login;

import io.reactivex.Observable;
import vn.tale.architecture.common.mvp.MvpView;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

interface LoginView extends MvpView<LoginViewState> {

  Observable<CharSequence> emailChanges();

  Observable<CharSequence> passwordChanges();

  Observable<Object> signInClick();
}
