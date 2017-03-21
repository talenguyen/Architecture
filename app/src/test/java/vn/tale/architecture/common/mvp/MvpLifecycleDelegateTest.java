package vn.tale.architecture.common.mvp;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by Giang Nguyen on 3/20/17.
 */
@SuppressWarnings("unchecked") public class MvpLifecycleDelegateTest {

  private MvpLifecycleDelegate<Object, MvpView<Object>> tested;
  private MvpPresenter mockedMvpPresenter;
  private MvpView mockedMvpView;

  @Before
  public void setUp() throws Exception {
    mockedMvpPresenter = mock(MvpPresenter.class);
    mockedMvpView = mock(MvpView.class);
    tested = new MvpLifecycleDelegate<>(mockedMvpPresenter, mockedMvpView);
  }

  @Test
  public void should_attach_view_onStart() throws Exception {
    tested.onStart();

    verify(mockedMvpPresenter).attachView(mockedMvpView);
  }

  @Test
  public void should_detach_view_onStop() throws Exception {
    tested.onStop(true);

    verify(mockedMvpPresenter).detachView();
  }

  @Test
  public void should_stop_presenter_onStop_finishing() throws Exception {
    tested.onStop(true);

    verify(mockedMvpPresenter).stop();
  }

  @Test
  public void should_ignore_onStop_not_finishing() throws Exception {
    tested.onStop(false);

    verify(mockedMvpPresenter, never()).stop();
  }
}