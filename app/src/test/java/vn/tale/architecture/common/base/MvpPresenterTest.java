package vn.tale.architecture.common.base;

import io.reactivex.disposables.Disposable;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Giang Nguyen on 2/21/17.
 */
public class MvpPresenterTest {

  Object mockedView;
  Disposable mockedDisposable;
  MvpPresenter<Object> tested;

  @Before
  public void setUp() throws Exception {
    mockedView = mock(Object.class);
    mockedDisposable = mock(Disposable.class);
    tested = new MvpPresenter<>();

    tested.attachView(mockedView);
    tested.disposeOnDetach(mockedDisposable);
  }

  @Test
  public void should_return_view_when_attached() throws Exception {
    assertThat(tested.getView()).isNotNull();
  }

  @Test
  public void should_return_null_when_detached() throws Exception {
    tested.detachView();

    assertThat(tested.getView()).isNull();
  }

  @Test
  public void should_dispose_disposable_when_detach() throws Exception {
    tested.detachView();
    verify(mockedDisposable).dispose();
  }
}