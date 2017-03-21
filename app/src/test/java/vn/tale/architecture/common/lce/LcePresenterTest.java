package vn.tale.architecture.common.lce;

import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static vn.tale.architecture.common.lce.LceViewState.content;
import static vn.tale.architecture.common.lce.LceViewState.error;
import static vn.tale.architecture.common.lce.LceViewState.loading;

/**
 * Created by Giang Nguyen on 3/20/17.
 */
@SuppressWarnings("unchecked") public class LcePresenterTest {

  private LceContract.Model mockedModel;
  private LcePresenter tested;
  private LceContract.View mockedView;
  private PublishSubject<Object> loadIntentSubject;

  @Before
  public void setUp() throws Exception {
    mockedModel = mock(LceContract.Model.class);
    mockedView = mock(LceContract.View.class);
    loadIntentSubject = PublishSubject.create();

    when(mockedView.loadIntent()).thenReturn(loadIntentSubject);

    tested = new LcePresenter<>(mockedModel);
    tested.attachView(mockedView);
  }

  @Test
  public void load_success() throws Exception {
    final Object state = new Object();
    when(mockedModel.getContent()).thenReturn(Single.just(state));

    loadIntentSubject.onNext(new Object());

    final InOrder inOrder = inOrder(mockedView);
    inOrder.verify(mockedView).render(eq(loading()));
    inOrder.verify(mockedView).render(eq(content(state)));
  }

  @Test
  public void load_error() throws Exception {
    final Throwable throwable = mock(Throwable.class);
    when(mockedModel.getContent()).thenReturn(Single.error(throwable));

    loadIntentSubject.onNext(new Object());

    final InOrder inOrder = inOrder(mockedView);
    inOrder.verify(mockedView).render(eq(loading()));
    inOrder.verify(mockedView).render(eq(error(throwable)));
  }
}