package vn.tale.architecture.repos.my;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
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
 * Created by Giang Nguyen on 2/25/17.
 */
public class MyReposPresenterTest {

  private static final String EMAIL = "foo@tale.vn";
  User mockedUser;
  List<Repo> mockedRepos;
  Throwable mockedError;
  UserModel mockedUserModel;
  RepoModel mockedRepoModel;
  Subject<Object> mockedLoadReposAction;
  MyReposView mockedPublicReposView;
  MyReposPresenter tested;

  @Before
  public void setUp() throws Exception {
    mockedUser = mock(User.class);
    mockedError = mock(Throwable.class);
    mockedRepoModel = mock(RepoModel.class);
    mockedUserModel = mock(UserModel.class);
    mockedPublicReposView = mock(MyReposView.class);
    mockedLoadReposAction = PublishSubject.create();
    tested = new MyReposPresenter(mockedUserModel, mockedRepoModel, SchedulerObservableTransformer.TEST);

    mockedRepos = Collections.singletonList(mock(Repo.class));
    when(mockedUser.getEmail()).thenReturn(EMAIL);
    when(mockedUserModel.user()).thenReturn(Observable.just(mockedUser));
    when(mockedPublicReposView.loadRepos()).thenReturn(mockedLoadReposAction);
    when(mockedRepoModel.getUserRepos(EMAIL)).thenReturn(Single.just(mockedRepos));

    tested.attachView(mockedPublicReposView);
  }

  @Test
  public void should_load_private_repos() throws Exception {
    mockedLoadReposAction.onNext(new Object());

    verify(mockedRepoModel).getUserRepos(EMAIL);
  }

  @Test
  public void should_show_loading_then_repos() throws Exception {
    mockedLoadReposAction.onNext(new Object());

    verify(mockedPublicReposView).showLoading();
    verify(mockedPublicReposView).showRepos(mockedRepos);
  }

  @Test
  public void should_show_loading_then_error_when_load_error() throws Exception {
    when(mockedRepoModel.getUserRepos(EMAIL)).thenReturn(Single.<List<Repo>>error(mockedError));

    mockedLoadReposAction.onNext(new Object());

    verify(mockedPublicReposView).showLoading();
    verify(mockedPublicReposView).showError(mockedError);
  }
}