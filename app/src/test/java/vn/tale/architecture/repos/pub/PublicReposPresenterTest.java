package vn.tale.architecture.repos.pub;

import io.reactivex.schedulers.Schedulers;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created by Giang Nguyen on 3/8/17.
 */
public class PublicReposPresenterTest {

  @Test
  public void should_no_exception_when_call_constructor() throws Exception {
    new PublicReposPresenter(
        mock(PublicReposGetDataInteractor.class),
        Schedulers.trampoline(),
        Schedulers.trampoline(),
        true);
  }
}