package vn.tale.architecture.repos.pub;

import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import vn.tale.architecture.common.SchedulerObservableTransformer;
import vn.tale.architecture.model.Repo;
import vn.tale.architecture.model.manager.RepoModel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Giang Nguyen on 2/25/17.
 */
public class PublicReposPresenterTest {

  List<Repo> mockedRepos;
  Throwable mockedError;
  RepoModel mockedRepoModel;
  Subject<Object> mockedLoadReposAction;
  PublicReposView mockedPublicReposView;
  PublicReposPresenter tested;

  @Before
  public void setUp() throws Exception {
    mockedError = mock(Throwable.class);
    mockedRepoModel = mock(RepoModel.class);
    mockedPublicReposView = mock(PublicReposView.class);
    mockedLoadReposAction = PublishSubject.create();
    tested = new PublicReposPresenter(mockedRepoModel, SchedulerObservableTransformer.TEST);

    mockedRepos = Collections.singletonList(mock(Repo.class));
    when(mockedPublicReposView.loadRepos()).thenReturn(mockedLoadReposAction);
    when(mockedRepoModel.getPublicRepos()).thenReturn(Single.just(mockedRepos));

    tested.attachView(mockedPublicReposView);
  }

  @Test
  public void should_load_public_repos() throws Exception {
    mockedLoadReposAction.onNext(new Object());

    verify(mockedRepoModel).getPublicRepos();
  }

  @Test
  public void should_show_loading_then_repos() throws Exception {
    mockedLoadReposAction.onNext(new Object());

    verify(mockedPublicReposView).showLoading();
    verify(mockedPublicReposView).showRepos(mockedRepos);
  }

  @Test
  public void should_show_loading_then_error_when_load_error() throws Exception {
    when(mockedRepoModel.getPublicRepos()).thenReturn(Single.<List<Repo>>error(mockedError));
    mockedLoadReposAction.onNext(new Object());

    verify(mockedPublicReposView).showLoading();
    verify(mockedPublicReposView).showError(mockedError);
  }

}