package vn.tale.architecture.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindString;
import butterknife.BindView;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import io.reactivex.functions.Action;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import vn.tale.architecture.App;
import vn.tale.architecture.R;
import vn.tale.architecture.common.base.ReduxActivity;
import vn.tale.architecture.common.dagger.DaggerComponentFactory;
import vn.tale.architecture.common.mvvm.Store;
import vn.tale.architecture.login.action.CheckEmailAction;
import vn.tale.architecture.login.action.SubmitAction;
import vn.tale.architecture.model.error.AuthenticateError;
import vn.tale.architecture.model.error.InvalidEmailError;
import vn.tale.architecture.model.error.OnErrorNotImplementedException;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class LoginActivity extends ReduxActivity<LoginComponent, LoginUiState> {

  @BindView(R.id.etEmail) TextInputEditText etEmail;
  @BindView(R.id.tilEmailWrapper) TextInputLayout tilEmailWrapper;
  @BindView(R.id.etPassword) TextInputEditText etPassword;
  @BindView(R.id.pbProgress) View pbProgress;
  @BindView(R.id.btSignIn) Button btSignIn;
  @BindString(R.string.successfully) String textSuccessfully;
  @BindString(R.string.email_is_invalid) String textEmailIsInvalid;
  @BindString(R.string.email_and_password_are_mismatched) String textEmailAndPasswordAreMismatch;

  @Inject Store<LoginUiState> store;

  @Override protected DaggerComponentFactory<LoginComponent> daggerComponentFactory() {
    return () -> App.get(this).getAppComponent().plus(new LoginModule());
  }

  @Override protected void injectDependencies() {
    daggerComponent().inject(this);
  }

  @Override protected Store<LoginUiState> store() {
    return store;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    bindViews(this);
  }

  @Override protected void onStart() {
    super.onStart();

    disposeOnStop(RxTextView.textChanges(etEmail)
        .debounce(200, TimeUnit.MILLISECONDS)
        .map(email -> new CheckEmailAction(email.toString()))
        .subscribe(action -> store.dispatch(action)));

    disposeOnStop(RxView.clicks(btSignIn)
        .map(ignored -> new SubmitAction(
            etEmail.getText().toString(),
            etPassword.getText().toString()))
        .subscribe(action -> store.dispatch(action)));
  }

  @NonNull public Action render(LoginUiState state) {
    if (state.inProgress) {
      return renderLoading();
    } else if (state.success) {
      return renderSuccess();
    } else if (state.error != null) {
      return renderError(state.error);
    } else {
      return renderIdle();
    }
  }

  @NonNull private Action renderLoading() {
    return () -> {
      btSignIn.setVisibility(View.GONE);
      pbProgress.setVisibility(View.VISIBLE);
    };
  }

  @NonNull private Action renderSuccess() {
    return () -> {
      Toast.makeText(this, textSuccessfully, Toast.LENGTH_SHORT).show();
      finish();
    };
  }

  @NonNull private Action renderError(Throwable error) {
    return () -> {
      btSignIn.setVisibility(View.VISIBLE);
      pbProgress.setVisibility(View.GONE);

      if (error instanceof InvalidEmailError) {
        tilEmailWrapper.setError(textEmailIsInvalid);
      } else if (error instanceof AuthenticateError) {
        tilEmailWrapper.setError(textEmailAndPasswordAreMismatch);
      } else {
        throw new OnErrorNotImplementedException(error);
      }
    };
  }

  @NonNull private Action renderIdle() {
    return () -> {
      btSignIn.setVisibility(View.VISIBLE);
      pbProgress.setVisibility(View.GONE);
      tilEmailWrapper.setError(null);
    };
  }
}
