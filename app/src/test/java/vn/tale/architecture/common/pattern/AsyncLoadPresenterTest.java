package vn.tale.architecture.common.pattern;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Giang Nguyen on 3/3/17.
 */
public class AsyncLoadPresenterTest {
  Object mockedData;
  Throwable mockedError;
  AsyncLoad.GetDataInteractor mockedGetDataInteractor;
  Subject<Object> mockedLoadAction;
  Subject<Object> mockedDestroyAction;
  AsyncLoad.View mockedView;
  AsyncLoadPresenter tested;

  @Before
  public void setUp() throws Exception {
    mockedError = mock(Throwable.class);
    mockedGetDataInteractor = mock(AsyncLoad.GetDataInteractor.class);
    mockedView = mock(AsyncLoad.View.class);
    mockedLoadAction = PublishSubject.create();
    mockedDestroyAction = PublishSubject.create();
    tested = new AsyncLoadPresenter<>(
        mockedGetDataInteractor,
        Schedulers.trampoline(),
        Schedulers.trampoline(),
        true);

    mockedData = mock(Object.class);
    when(mockedView.onLoad()).thenReturn(mockedLoadAction);
    when(mockedView.onDestroy()).thenReturn(mockedDestroyAction);
    when(mockedGetDataInteractor.getData()).thenReturn(Observable.just(mockedData));

    tested.attachView(mockedView);
  }

  @Test
  public void should_return_no_op_view_when_detached() throws Exception {
    tested.detachView();

    assertThat(tested.getView()).isInstanceOf(NoOpView.class);
  }

  @Test
  public void should_clean_on_destroy() throws Exception {
    mockedDestroyAction.onNext(new Object());

    assertThat(tested.getView()).isInstanceOf(NoOpView.class);
  }

  @Test
  public void should_show_loading_then_repos() throws Exception {
    mockedLoadAction.onNext(new Object());

    verify(mockedView).showLoading();
    verify(mockedView).showContent(mockedData);
  }

  @Test
  public void should_show_loading_then_error_when_load_error() throws Exception {
    when(mockedGetDataInteractor.getData()).thenReturn(Observable.error(mockedError));
    mockedLoadAction.onNext(new Object());

    verify(mockedView).showLoading();
    verify(mockedView).showError(mockedError);
  }

  @Test
  public void should_reloadable_when_error_occurred() throws Exception {
    when(mockedGetDataInteractor.getData()).thenReturn(Observable.error(mockedError));
    mockedLoadAction.onNext(new Object());

    when(mockedGetDataInteractor.getData()).thenReturn(Observable.just(mockedData));
    mockedLoadAction.onNext(new Object());

    verify(mockedView, times(2)).showLoading();
    verify(mockedView).showContent(mockedData);
  }

  @Test
  public void should_not_send_request_when_data_is_cached() throws Exception {
    mockedLoadAction.onNext(new Object());

    verify(mockedView).showLoading();
    verify(mockedView).showContent(mockedData);

    mockedLoadAction.onNext(new Object());

    verify(mockedView).showLoading();
    verify(mockedView, times(2)).showContent(mockedData);
  }
}