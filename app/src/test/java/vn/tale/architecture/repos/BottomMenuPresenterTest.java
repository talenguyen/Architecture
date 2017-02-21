package vn.tale.architecture.repos;

import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import vn.tale.architecture.model.User;
import vn.tale.architecture.model.manager.UserModel;
import vn.tale.architecture.repos.menu.bottom.BottomMenuPresenter;
import vn.tale.architecture.repos.menu.bottom.BottomMenuView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Giang Nguyen on 2/21/17.
 */
public class BottomMenuPresenterTest {
  UserModel mockedUserModel;
  User mockedUser;
  BottomMenuPresenter tested;
  private BottomMenuView mockedBottomMenuView;

  @Before
  public void setUp() throws Exception {
    mockedUserModel = mock(UserModel.class);
    mockedUser = mock(User.class);
    mockedBottomMenuView = mock(BottomMenuView.class);
    tested = new BottomMenuPresenter(mockedUserModel);

    when(mockedUserModel.user()).thenReturn(Observable.just(UserModel.ANNOYMOUS));

    tested.attachView(mockedBottomMenuView);
  }

  @Test
  public void should_hide_user_repos_when_not_login() throws Exception {
    verify(mockedBottomMenuView).hideUserRepos();
  }

  @Test
  public void should_show_user_repo_when_user_logged_in() throws Exception {
    when(mockedUserModel.user()).thenReturn(Observable.just(mockedUser));
    tested.attachView(mockedBottomMenuView);

    verify(mockedBottomMenuView).showUserRepos();
  }
}