package vn.tale.architecture.common.mvp;

import io.reactivex.disposables.Disposable;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Giang Nguyen on 3/20/17.
 */
@SuppressWarnings("unchecked") public class MvpPresenter_DetachTest {

  private MvpPresenter<Object, MvpView<Object>> tested;

  @Before
  public void setUp() throws Exception {
    tested = new MvpPresenter<>();
    tested.attachView(mock(MvpView.class));
  }

  @Test
  public void view_should_null() throws Exception {
    tested.detachView();

    final MvpView<Object> view = tested.getView();
    assertThat(view).isNull();
  }

  @Test
  public void all_disposables_should_disposed() throws Exception {
    final Disposable disposable1 = mock(Disposable.class);
    final Disposable disposable2 = mock(Disposable.class);

    tested.disposeOnDetach(disposable1);
    tested.disposeOnDetach(disposable2);
    tested.detachView();

    verify(disposable1).dispose();
    verify(disposable2).dispose();
  }
}