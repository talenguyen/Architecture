package vn.tale.architecture.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindString;
import butterknife.BindView;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import io.reactivex.Observable;
import javax.inject.Inject;
import vn.tale.architecture.App;
import vn.tale.architecture.R;
import vn.tale.architecture.common.base.MvpActivity;
import vn.tale.architecture.common.dagger.DaggerComponentFactory;
import vn.tale.architecture.common.mvp.MvpPresenter;
import vn.tale.architecture.model.error.AuthenticateError;
import vn.tale.architecture.model.error.InvalidEmailError;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class LoginActivity extends MvpActivity<LoginComponent, LoginViewState, LoginView>
    implements LoginView {

  @BindView(R.id.etEmail) TextInputEditText etEmail;
  @BindView(R.id.tilEmailWrapper) TextInputLayout tilEmailWrapper;
  @BindView(R.id.etPassword) TextInputEditText etPassword;
  @BindView(R.id.btSignIn) Button btSignIn;
  @BindString(R.string.successfully) String textSuccessfully;
  @BindString(R.string.email_is_invalid) String textEmailIsInvalid;
  @BindString(R.string.email_and_password_are_mismatched) String textEmailAndPasswordAreMismatch;

  @Inject LoginPresenter loginPresenter;

  @Override protected DaggerComponentFactory<LoginComponent> daggerComponentFactory() {
    return () -> App.get(this).getAppComponent().plus(new LoginModule());
  }

  @Override protected MvpPresenter<LoginViewState, LoginView> presenter() {
    return loginPresenter;
  }

  @Override protected LoginView mvpView() {
    return this;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    bindViews(this);
    daggerComponent().inject(this);
  }

  @Override public Observable<CharSequence> emailChanges() {
    return RxTextView.textChanges(etEmail);
  }

  @Override public Observable<CharSequence> passwordChanges() {
    return RxTextView.textChanges(etPassword);
  }

  @Override public Observable<Object> signInClick() {
    return RxView.clicks(btSignIn);
  }

  @SuppressWarnings("ThrowableResultOfMethodCallIgnored") @Override
  public void render(LoginViewState state) {
    if (state.success()) {
      Toast.makeText(this, textSuccessfully, Toast.LENGTH_SHORT).show();
      finish();
      return;
    }

    final Throwable error = state.error();

    if (error instanceof InvalidEmailError) {
      tilEmailWrapper.setError(textEmailIsInvalid);
      btSignIn.setEnabled(false);
      return;
    }

    tilEmailWrapper.setError(null);
    btSignIn.setEnabled(true);

    if (error instanceof AuthenticateError) {
      tilEmailWrapper.setError(textEmailAndPasswordAreMismatch);
    }
  }
}
