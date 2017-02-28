package vn.tale.architecture.login;

import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import org.junit.Test;
import vn.tale.architecture.common.EmailValidator;
import vn.tale.architecture.common.SchedulerSingleTransformer;
import vn.tale.architecture.model.User;
import vn.tale.architecture.model.exeption.UserNotFoundException;
import vn.tale.architecture.model.manager.UserModel;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Giang Nguyen on 2/21/17.
 */
public class LoginPresenterRobotTest {

  private static final String INVALID_EMAIL = "foo@bar";
  private static final String VALID_EMAIL = "foo@tiki.vn";
  private static final String VALID_PASSWORD = "123456";
  private static final String INVALID_PASSWORD = "123";

  @Test
  public void should_disable_login_button_by_default() throws Exception {
    loginScreen()
        .assertLoginView()
        .loginButtonDisabled();
  }

  @Test
  public void should_validate_email_realtime() throws Exception {
    loginScreen()
        .inputEmail(INVALID_EMAIL)
        .assertLoginView()
        .invalidEmailMessageShowed();

    loginScreen()
        .inputEmail(VALID_EMAIL)
        .assertLoginView()
        .invalidEmailMessageNotShowed()
        .loginButtonEnabled();
  }

  @Test
  public void should_show_error_when_login_fail() throws Exception {
    loginScreen()
        .inputEmail(VALID_EMAIL)
        .inputPassword(INVALID_PASSWORD)
        .submit()
        .assertLoginView()
        .loginFailMessageShowed();
  }

  @Test
  public void should_hide_when_login_success() throws Exception {
    loginScreen()
        .inputEmail(VALID_EMAIL)
        .inputPassword(VALID_PASSWORD)
        .submit()
        .assertLoginView()
        .loginSuccessMessageShowed()
        .hidden();
  }

  private LoginRobot loginScreen() {
    return new LoginRobot();
  }

  private static class LoginRobot {
    LoginView mockedLoginView;
    UserModel mockedUserModel;
    Subject<CharSequence> emailStream;
    Subject<CharSequence> passwordStream;
    Subject<Object> signInClick;
    LoginPresenter tested;

    LoginRobot() {
      mockedLoginView = mock(LoginView.class);
      mockedUserModel = mock(UserModel.class);
      emailStream = BehaviorSubject.<CharSequence>createDefault("");
      passwordStream = BehaviorSubject.<CharSequence>createDefault("");
      signInClick = PublishSubject.create();
      tested = new LoginPresenter(
          mockedUserModel,
          new EmailValidator(),
          SchedulerSingleTransformer.TEST);

      when(mockedLoginView.emailChanges()).thenReturn(emailStream);
      when(mockedLoginView.passwordChanges()).thenReturn(passwordStream);
      when(mockedLoginView.signInClick()).thenReturn(signInClick);
      when(mockedUserModel.login(eq(VALID_EMAIL), eq(VALID_PASSWORD)))
          .thenReturn(Single.just(mock(User.class)));
      when(mockedUserModel.login(eq(VALID_EMAIL), eq(INVALID_PASSWORD)))
          .thenReturn(Single.<User>error(new UserNotFoundException("")));

      tested.attachView(mockedLoginView);
    }

    LoginRobot inputEmail(String email) {
      emailStream.onNext(email);
      return this;
    }

    LoginRobot inputPassword(String password) {
      passwordStream.onNext(password);
      return this;
    }

    LoginRobot submit() {
      signInClick.onNext(new Object());
      return this;
    }

    LoginAssertion assertLoginView() {
      return new LoginAssertion(mockedLoginView);
    }

  }

  private static class LoginAssertion {

    LoginView mockedLoginView;

    LoginAssertion(LoginView mockedLoginView) {
      this.mockedLoginView = mockedLoginView;
    }

    LoginAssertion invalidEmailMessageShowed() {
      verify(mockedLoginView).showInvalidEmailError();
      return this;
    }

    LoginAssertion invalidEmailMessageNotShowed() {
      verify(mockedLoginView).hideEmailError();
      return this;
    }

    LoginAssertion loginFailMessageShowed() {
      verify(mockedLoginView).showLoginFailMessage();
      return this;
    }

    LoginAssertion loginSuccessMessageShowed() {
      verify(mockedLoginView).showSuccessMessage();
      return this;
    }

    LoginAssertion loginButtonEnabled() {
      verify(mockedLoginView).enableSignInButton();
      return this;
    }

    LoginAssertion loginButtonDisabled() {
      verify(mockedLoginView).disableSignInButton();
      return this;
    }

    void hidden() {
      verify(mockedLoginView).hide();
    }
  }
}