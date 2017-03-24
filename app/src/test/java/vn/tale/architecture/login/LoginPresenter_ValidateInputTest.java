package vn.tale.architecture.login;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import org.junit.Before;
import org.junit.Test;
import vn.tale.architecture.common.EmailValidator;
import vn.tale.architecture.model.error.InvalidEmailError;
import vn.tale.architecture.model.manager.UserModel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Giang Nguyen on 2/21/17.
 */
public class LoginPresenter_ValidateInputTest {

  private static final String INVALID_EMAIL = "foo@";
  private static final String VALID_EMAIL = "foo@tiki.vn";

  LoginView mockedLoginView;
  Subject<CharSequence> emailStream;
  Subject<CharSequence> passwordStream;
  LoginPresenter tested;

  @Before
  public void setUp() throws Exception {
    mockedLoginView = mock(LoginView.class);
    emailStream = BehaviorSubject.createDefault("");
    passwordStream = BehaviorSubject.createDefault("");
    tested = new LoginPresenter(
        mock(UserModel.class),
        new EmailValidator()
    );

    when(mockedLoginView.emailChanges()).thenReturn(emailStream);
    when(mockedLoginView.passwordChanges()).thenReturn(passwordStream);
    when(mockedLoginView.signInClick()).thenReturn(Observable.empty());

    tested.attachView(mockedLoginView);
  }

  @Test
  public void invalid_email_THEN_render_error_state() throws Exception {
    emailStream.onNext(INVALID_EMAIL);

    verify(mockedLoginView).render(LoginUiModel.error(new InvalidEmailError()));
  }

  @Test
  public void valid_email_THEN_render_idle_state() throws Exception {
    emailStream.onNext(VALID_EMAIL);

    verify(mockedLoginView).render(LoginUiModel.idle());
  }
}