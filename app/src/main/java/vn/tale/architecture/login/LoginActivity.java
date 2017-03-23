package vn.tale.architecture.login;

import android.os.Bundle;
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
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import vn.tale.architecture.App;
import vn.tale.architecture.R;
import vn.tale.architecture.common.EmailValidator;
import vn.tale.architecture.common.base.MvpActivity;
import vn.tale.architecture.common.dagger.DaggerComponentFactory;
import vn.tale.architecture.common.mvp.MvpPresenter;
import vn.tale.architecture.model.error.AuthenticateError;
import vn.tale.architecture.model.error.InvalidEmailError;
import vn.tale.architecture.model.error.OnErrorNotImplementedException;
import vn.tale.architecture.model.manager.UserModel;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class LoginActivity extends MvpActivity<LoginComponent, LoginUiState, LoginView>
    implements LoginView {

  @BindView(R.id.etEmail) TextInputEditText etEmail;
  @BindView(R.id.tilEmailWrapper) TextInputLayout tilEmailWrapper;
  @BindView(R.id.etPassword) TextInputEditText etPassword;
  @BindView(R.id.pbProgress) View pbProgress;
  @BindView(R.id.btSignIn) Button btSignIn;
  @BindString(R.string.successfully) String textSuccessfully;
  @BindString(R.string.email_is_invalid) String textEmailIsInvalid;
  @BindString(R.string.email_and_password_are_mismatched) String textEmailAndPasswordAreMismatch;

  @Inject LoginPresenter loginPresenter;
  @Inject UserModel userModel;

  @Override protected DaggerComponentFactory<LoginComponent> daggerComponentFactory() {
    return () -> App.get(this).getAppComponent().plus(new LoginModule());
  }

  @Override protected MvpPresenter<LoginUiState, LoginView> presenter() {
    return loginPresenter;
  }

  @Override protected void injectDependencies() {
    daggerComponent().inject(this);
  }

  @Override protected LoginView mvpView() {
    return this;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    bindViews(this);
    daggerComponent().inject(this);

    //final Observable<SubmitAction> submitEvents = RxView.clicks(btSignIn)
    //    .map(ignored -> new SubmitAction(
    //        etEmail.getText().toString(),
    //        etPassword.getText().toString()));
    //
    //final Observable<CheckEmailAction> checkEmailEvents = RxTextView.textChanges(etEmail)
    //    .map(text -> new CheckEmailAction(text.toString()));
    //
    //final Observable<Action> events = Observable.merge(submitEvents, checkEmailEvents);
    //
    //final ObservableTransformer<SubmitAction, SubmitResult> submit =
    //    submitEvents -> submitEvents
    //        .flatMap(event -> userModel.login(
    //            event.email,
    //            event.password)
    //            .map(response -> SubmitResult.SUCCESS)
    //            .onErrorReturn(SubmitResult::error)
    //            .subscribeOn(Schedulers.io())
    //            .observeOn(AndroidSchedulers.mainThread())
    //            .startWith(SubmitResult.IN_FLIGHT));
    //
    //final ObservableTransformer<CheckEmailAction, CheckEmailResult> checkEmail =
    //    checkEmailEvents -> checkEmailEvents
    //        .flatMap(event -> validate(event.email)
    //            .map(response -> CheckEmailResult.SUCCESS)
    //            .onErrorReturn(CheckEmailResult::error)
    //            .subscribeOn(Schedulers.io())
    //            .observeOn(AndroidSchedulers.mainThread())
    //            .startWith(CheckEmailResult.IN_FLIGHT));
    //
    //final ObservableTransformer<Action, Result> results =
    //    events -> events.publish(shared -> Observable.merge(
    //        shared.ofType(SubmitAction.class).compose(submit),
    //        shared.ofType(CheckEmailAction.class).compose(checkEmail)));
    //
    //LoginUiState initialState = LoginUiState.idle();
    //
    //events.compose(results)
    //    .scan(initialState, (state, result) -> {
    //      if (result == SubmitResult.IN_FLIGHT) {
    //        return LoginUiState.inProgress();
    //      } else if (result == CheckEmailResult.SUCCESS) {
    //        return LoginUiState.idle();
    //      } else if (result == SubmitResult.SUCCESS) {
    //        return LoginUiState.success();
    //      }
    //      return LoginUiState.error(result.error());
    //    })
    //    .subscribe(this::render, t -> { throw new OnErrorNotImplementedException(t); });
  }

  private Observable<Object> validate(String email) {
    return Observable.fromCallable(() -> {
      if (new EmailValidator().isValid(email)) {
        return true;
      }
      throw new InvalidEmailError();
    });
  }

  @Override public Observable<CharSequence> emailChanges() {
    return RxTextView.textChanges(etEmail);
  }

  @Override public Observable<CharSequence> passwordChanges() {
    return RxTextView.textChanges(etPassword)
        .debounce(200, TimeUnit.MILLISECONDS);
  }

  @Override public Observable<Object> signInClick() {
    return RxView.clicks(btSignIn);
  }

  @SuppressWarnings("ThrowableResultOfMethodCallIgnored") @Override
  public void render(LoginUiState state) {
    runOnUiThread(() -> {
      if (state.inProgress) {
        renderLoading();
      } else if (state.success) {
        renderSuccess();
      } else if (state.error != null) {
        renderError(state.error);
      } else {
        renderIdle();
      }
    });
  }

  private void renderIdle() {
    btSignIn.setVisibility(View.VISIBLE);
    pbProgress.setVisibility(View.GONE);
    tilEmailWrapper.setError(null);
  }

  private void renderError(Throwable error) {
    btSignIn.setVisibility(View.VISIBLE);
    pbProgress.setVisibility(View.GONE);

    if (error instanceof InvalidEmailError) {
      tilEmailWrapper.setError(textEmailIsInvalid);
    } else if (error instanceof AuthenticateError) {
      tilEmailWrapper.setError(textEmailAndPasswordAreMismatch);
    } else {
      throw new OnErrorNotImplementedException(error);
    }
  }

  private void renderSuccess() {
    Toast.makeText(this, textSuccessfully, Toast.LENGTH_SHORT).show();
    finish();
  }

  private void renderLoading() {
    btSignIn.setVisibility(View.GONE);
    pbProgress.setVisibility(View.VISIBLE);
  }
}
