package vn.tale.architecture.repos.menu.top;

import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import vn.tale.architecture.model.User;
import vn.tale.architecture.model.manager.UserModel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Giang Nguyen on 2/21/17.
 */
public class TopMenuPresenterTest {

  User mockedUser;
  UserModel mockedUserModel;
  TopMenuView mockedTopMenuView;

  TopMenuPresenter tested;

  @Before
  public void setUp() throws Exception {
    mockedUserModel = mock(UserModel.class);
    mockedUser = mock(User.class);
    mockedTopMenuView = mock(TopMenuView.class);
    tested = new TopMenuPresenter(mockedUserModel);

    when(mockedUserModel.user()).thenReturn(Observable.just(UserModel.ANNOYMOUS));
  }

  @Test
  public void should_show_login_menu_when_not_login() throws Exception {
    tested.attachView(mockedTopMenuView);

    verify(mockedTopMenuView).showLoginMenu();
  }

  @Test
  public void should_show_logout_menu_when_logged_in() throws Exception {
    when(mockedUserModel.user()).thenReturn(Observable.just(mockedUser));

    tested.attachView(mockedTopMenuView);

    verify(mockedTopMenuView).showLogoutMenu();
  }
}