package vn.tale.architecture.common.mvp;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by Giang Nguyen on 3/20/17.
 */
public class MvpPresenter_SetStateTest {

  private MvpPresenter<Object, MvpView<Object>> tested;

  @Before
  public void setUp() throws Exception {
    tested = new MvpPresenter<>();
  }

  @Test
  public void should_cache_state() throws Exception {
    final Object state = new Object();
    tested.setState(state);

    assertThat(tested.state).isEqualTo(state);
  }
}