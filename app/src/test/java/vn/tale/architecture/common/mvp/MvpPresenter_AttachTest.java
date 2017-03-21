package vn.tale.architecture.common.mvp;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by Giang Nguyen on 3/20/17.
 */
@SuppressWarnings("unchecked") public class MvpPresenter_AttachTest {

  private MvpPresenter<Object, MvpView<Object>> tested;
  private MvpView<Object> mockedView;

  @Before
  public void setUp() throws Exception {
    mockedView = mock(MvpView.class);
    tested = new MvpPresenter<>();
  }

  @Test
  public void view_should_be_set() throws Exception {
    tested.attachView(mockedView);

    final MvpView<Object> view = tested.getView();
    assertThat(view).isEqualTo(mockedView);
  }

  @Test
  public void state_not_null_THEN_render_state() throws Exception {
    final Object state = new Object();

    tested.setState(state);
    tested.attachView(mockedView);

    verify(mockedView).render(eq(state));
  }

  @Test
  public void state_null_THEN_ignore() throws Exception {
    tested.attachView(mockedView);

    verifyNoMoreInteractions(mockedView);
  }
}