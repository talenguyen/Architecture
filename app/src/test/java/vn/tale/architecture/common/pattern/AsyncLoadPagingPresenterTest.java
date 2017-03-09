package vn.tale.architecture.common.pattern;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static vn.tale.architecture.common.CollectionsX.concat;

/**
 * Created by Giang Nguyen on 3/8/17.
 */
public class AsyncLoadPagingPresenterTest {

  private AsyncLoadPagingPresenter tested;
  private AsyncLoadPaging.View mockedView;
  private PublishSubject<Object> onLoadEmitter;
  private PublishSubject<Object> onLoadMoreEmitter;
  private PublishSubject<Object> onRefreshEmitter;
  private PublishSubject<Object> onDestroyedEmitter;
  private List mockedPage1Items = Arrays.asList("1", "2");
  private List mockedPage2Items = Arrays.asList("3", "4");
  private AsyncLoadPaging.GetDataInteractor mockedGetDataInteractor;
  private Throwable mockedError;

  @Before
  public void setUp() throws Exception {
    onLoadEmitter = PublishSubject.create();
    onLoadMoreEmitter = PublishSubject.create();
    onRefreshEmitter = PublishSubject.create();
    onDestroyedEmitter = PublishSubject.create();
    mockedView = mock(AsyncLoadPaging.View.class);
    mockedGetDataInteractor = mock(AsyncLoadPaging.GetDataInteractor.class);
    mockedError = mock(Throwable.class);

    when(mockedView.onLoad()).thenReturn(onLoadEmitter);
    when(mockedView.onLoadMore()).thenReturn(onLoadMoreEmitter);
    when(mockedView.onRefresh()).thenReturn(onRefreshEmitter);
    when(mockedView.onDestroy()).thenReturn(onDestroyedEmitter);
    when(mockedGetDataInteractor.itemsOfPage(eq(1))).thenReturn(Observable.just(mockedPage1Items));
    when(mockedGetDataInteractor.itemsOfPage(eq(2))).thenReturn(Observable.just(mockedPage2Items));

    tested = new AsyncLoadPagingPresenter(mockedGetDataInteractor);
    tested.attachView(mockedView);
  }

  @Test
  public void should_return_no_op_view_when_detached() throws Exception {
    tested.detachView();

    assertThat(tested.getView()).isInstanceOf(NoOpPagingView.class);
  }

  @Test
  public void should_clean_on_destroy() throws Exception {
    onDestroyedEmitter.onNext(new Object());

    assertThat(tested.getView()).isInstanceOf(NoOpPagingView.class);
  }

  @Test
  public void should_load_page_1_on_load() throws Exception {
    onLoadEmitter.onNext(new Object());

    verify(mockedGetDataInteractor).itemsOfPage(eq(1));
  }

  @Test
  public void should_show_loading_then_content_when_success() throws Exception {
    onLoadEmitter.onNext(new Object());

    verify(mockedView).showLoading();
    verify(mockedView).showContent(mockedPage1Items);
  }

  @Test
  public void should_show_loading_then_error_when_error() throws Exception {
    when(mockedGetDataInteractor.itemsOfPage(eq(1))).thenReturn(Observable.error(mockedError));

    onLoadEmitter.onNext(new Object());

    verify(mockedView).showLoading();
    verify(mockedView).showError(mockedError);
  }

  @Test
  public void should_reloadable_when_error_occurred() throws Exception {
    when(mockedGetDataInteractor.itemsOfPage(eq(1))).thenReturn(Observable.error(mockedError));
    onLoadEmitter.onNext(new Object());

    when(mockedGetDataInteractor.itemsOfPage(eq(1))).thenReturn(Observable.just(mockedPage1Items));
    onLoadEmitter.onNext(new Object());

    verify(mockedView, times(2)).showLoading();
    verify(mockedView).showContent(mockedPage1Items);
  }

  @Test
  public void should_load_next_page_when_load_more() throws Exception {
    onLoadMoreEmitter.onNext(new Object());
    verify(mockedGetDataInteractor).itemsOfPage(eq(2));

    onLoadMoreEmitter.onNext(new Object());
    verify(mockedGetDataInteractor).itemsOfPage(eq(3));
  }

  @Test
  public void should_not_increase_page_when_load_more_error() throws Exception {
    when(mockedGetDataInteractor.itemsOfPage(eq(2))).thenReturn(Observable.error(mockedError));

    onLoadMoreEmitter.onNext(new Object());
    verify(mockedGetDataInteractor).itemsOfPage(eq(2));

    onLoadMoreEmitter.onNext(new Object());
    verify(mockedGetDataInteractor, times(2)).itemsOfPage(eq(2));
  }

  @Test
  public void should_show_load_more_indicator_when_load_more() throws Exception {
    onLoadMoreEmitter.onNext(new Object());

    verify(mockedView).showLoadMoreIndicator();
  }

  @Test
  public void should_show_total_item_when_load_more_success() throws Exception {
    onLoadEmitter.onNext(new Object());
    onLoadMoreEmitter.onNext(new Object());

    verify(mockedView).showContent(concat(mockedPage1Items, mockedPage2Items));
  }

  @Test
  public void should_show_load_more_error_when_load_more_error() throws Exception {
    when(mockedGetDataInteractor.itemsOfPage(eq(2))).thenReturn(Observable.error(mockedError));

    onLoadEmitter.onNext(new Object());
    onLoadMoreEmitter.onNext(new Object());

    verify(mockedView).showLoadMoreError(mockedError);
  }

  @Test
  public void should_load_page_1_when_refresh() throws Exception {
    onRefreshEmitter.onNext(new Object());

    verify(mockedGetDataInteractor).itemsOfPage(eq(1));
  }

  @Test
  public void should_show_refresh_indicator_when_refresh() throws Exception {
    onRefreshEmitter.onNext(new Object());

    verify(mockedView).showRefreshIndicator();
  }

  @Test
  public void should_show_refresh_when_refresh_error() throws Exception {
    onLoadEmitter.onNext(new Object());

    when(mockedGetDataInteractor.itemsOfPage(eq(1))).thenReturn(Observable.error(mockedError));

    onRefreshEmitter.onNext(new Object());

    verify(mockedView).showRefreshError(mockedError);
  }

  @Test
  public void should_refresh_content_when_refresh() throws Exception {
    onLoadEmitter.onNext(new Object());
    onLoadMoreEmitter.onNext(new Object());
    onRefreshEmitter.onNext(new Object());

    verify(mockedView, times(2)).showContent(eq(mockedPage1Items));
  }
}