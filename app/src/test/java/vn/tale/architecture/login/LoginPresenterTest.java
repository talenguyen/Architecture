package vn.tale.architecture.login;

import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import org.junit.Before;
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
public class LoginPresenterTest {

  private static final String INVALID_EMAIL = "foo@";
  private static final String VALID_EMAIL = "foo@tiki.vn";
  private static final String VALID_PASSWORD = "123456";

  LoginView mockedLoginView;
  UserModel mockedUserModel;
  Subject<CharSequence> emailStream;
  Subject<CharSequence> passwordStream;
  Subject<Object> signInClick;
  LoginPresenter tested;

  @Before
  public void setUp() throws Exception {
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
    when(mockedUserModel.login(eq(INVALID_EMAIL), eq(VALID_PASSWORD)))
        .thenReturn(Single.<User>error(new UserNotFoundException("")));

    tested.attachView(mockedLoginView);
  }

  @Test
  public void should_disable_login_button_by_default() throws Exception {
    verify(mockedLoginView).disableSignInButton();
  }

  @Test
  public void should_show_email_error_when_email_invalid() throws Exception {
    emailStream.onNext(INVALID_EMAIL);

    verify(mockedLoginView).showInvalidEmailError();
  }

  @Test
  public void should_hide_email_error_android_enable_sign_button_when_email_is_corrected()
      throws Exception {
    emailStream.onNext(VALID_EMAIL);

    verify(mockedLoginView).hideEmailError();
    verify(mockedLoginView).enableSignInButton();
  }

  @Test
  public void should_show_error_when_login_fail() throws Exception {
    emailStream.onNext(INVALID_EMAIL);
    passwordStream.onNext(VALID_PASSWORD);
    signInClick.onNext(new Object());

    verify(mockedLoginView).showLoginFailMessage();
  }

  @Test
  public void should_success_message_then_hide_when_login_success() throws Exception {
    emailStream.onNext(VALID_EMAIL);
    passwordStream.onNext(VALID_PASSWORD);
    signInClick.onNext(new Object());

    verify(mockedLoginView).showSuccessMessage();
    verify(mockedLoginView).hide();
  }
}