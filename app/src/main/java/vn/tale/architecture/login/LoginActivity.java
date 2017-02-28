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
import vn.tale.architecture.AppComponent;
import vn.tale.architecture.R;
import vn.tale.architecture.common.base.BaseActivity;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class LoginActivity extends BaseActivity implements LoginView {

  @BindView(R.id.etEmail) TextInputEditText etEmail;
  @BindView(R.id.tilEmailWrapper) TextInputLayout tilEmailWrapper;
  @BindView(R.id.etPassword) TextInputEditText etPassword;
  @BindView(R.id.btSignIn) Button btSignIn;
  @BindString(R.string.email_is_invalid) String textEmailIsInvalid;

  @BindString(R.string.email_and_password_are_mismatched) String textEmailAndPasswordAreMismatch;

  @Inject LoginPresenter loginPresenter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    bindViews(this);
    injectDependencies();
  }

  @Override protected void onStart() {
    super.onStart();
    loginPresenter.attachView(this);
  }

  @Override protected void onStop() {
    loginPresenter.detachView();
    super.onStop();
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

  @Override public void showInvalidEmailError() {
    tilEmailWrapper.setError(textEmailIsInvalid);
  }

  @Override public void hideEmailError() {
    tilEmailWrapper.setError(null);
  }

  @Override public void showLoginFailMessage() {
    tilEmailWrapper.setError(textEmailAndPasswordAreMismatch);
  }

  @Override public void showSuccessMessage() {
    Toast.makeText(this, R.string.successfully, Toast.LENGTH_SHORT).show();
  }

  @Override public void disableSignInButton() {
    btSignIn.setEnabled(false);
  }

  @Override public void enableSignInButton() {
    btSignIn.setEnabled(true);
  }

  @Override public void hide() {
    finish();
  }

  private void injectDependencies() {
    final AppComponent appComponent = App.get(this).getAppComponent();
    appComponent.plus(new LoginModule()).inject(this);
  }
}
