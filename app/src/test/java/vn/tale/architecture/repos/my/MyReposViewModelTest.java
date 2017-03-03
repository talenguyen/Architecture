package vn.tale.architecture.repos.my;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import vn.tale.architecture.common.SchedulerObservableTransformer;
import vn.tale.architecture.model.Repo;
import vn.tale.architecture.model.User;
import vn.tale.architecture.model.manager.RepoModel;
import vn.tale.architecture.model.manager.UserModel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Giang Nguyen on 3/3/17.
 */
public class MyReposViewModelTest {

  private static final String EMAIL = "foo@tale.vn";
  private RepoModel mockedRepoModel;
  private MyReposViewModel tested;
  private List<Repo> mockedRepos;
  private TestObserver<Object> stateObserver;
  private Throwable mockedError;

  @Before
  public void setUp() throws Exception {
    User mockedUser = mock(User.class);
    mockedError = mock(Throwable.class);
    UserModel mockedUserModel = mock(UserModel.class);
    mockedRepoModel = mock(RepoModel.class);
    tested = new MyReposViewModel(mockedUserModel, mockedRepoModel,
        SchedulerObservableTransformer.TEST);

    mockedRepos = Collections.singletonList(mock(Repo.class));
    when(mockedUser.getEmail()).thenReturn(EMAIL);
    when(mockedUserModel.user()).thenReturn(Observable.just(mockedUser));
    when(mockedRepoModel.getUserRepos(EMAIL)).thenReturn(Single.just(mockedRepos));

    stateObserver = TestObserver.create();
    tested.getState().subscribe(stateObserver);
  }

  @Test
  public void should_load_private_repos() throws Exception {
    tested.loadRepos();

    verify(mockedRepoModel).getUserRepos(EMAIL);
  }

  @Test
  public void should_show_loading_then_repos() throws Exception {
    tested.loadRepos();

    stateObserver.assertValues(
        MyReposState.builder().loading(true).build(),
        MyReposState.builder().items(mockedRepos).build()
    );
  }

  @Test
  public void should_show_loading_then_error_when_load_error() throws Exception {
    when(mockedRepoModel.getUserRepos(EMAIL)).thenReturn(Single.<List<Repo>>error(mockedError));

    tested.loadRepos();

    stateObserver.assertValues(
        MyReposState.builder().loading(true).build(),
        MyReposState.builder().error(mockedError).build()
    );
  }
}