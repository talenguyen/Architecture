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
public class MvpPresenter_StopTest {

  private MvpPresenter<Object, MvpView<Object>> tested;
  private boolean onStopped;

  @Before
  public void setUp() throws Exception {
    tested = new MvpPresenter<Object, MvpView<Object>>() {

      @Override protected void onStop() {
        super.onStop();
        onStopped = true;
      }
    };
  }

  @Test
  public void should_call_onStop() throws Exception {
    tested.stop();
    assertThat(onStopped).isTrue();
  }

  @Test
  public void should_clear_state() throws Exception {
    tested.stop();

    assertThat(tested.state).isNull();
  }

  @Test
  public void should_dispose_all_disposables() throws Exception {
    final Disposable disposable1 = mock(Disposable.class);
    final Disposable disposable2 = mock(Disposable.class);

    tested.disposeOnStop(disposable1);
    tested.disposeOnStop(disposable2);
    tested.stop();

    verify(disposable1).dispose();
    verify(disposable2).dispose();
  }
}