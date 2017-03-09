package vn.tale.architecture;

import org.junit.Before;
import org.junit.Test;
import vn.tale.architecture.DaggerComponentManager.ComponentFactory;

import static com.google.common.truth.Truth.assertThat;
import static junit.framework.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Giang Nguyen on 3/9/17.
 */
public class DaggerComponentManagerTest {

  private ComponentFactory mockedComponentFactory;
  private DaggerComponentManager tested;
  private TestModule mockedTestModule;
  private TestComponent mockedTestComponent;

  @Before
  public void setUp() throws Exception {
    mockedComponentFactory = mock(ComponentFactory.class);
    mockedTestModule = mock(TestModule.class);
    mockedTestComponent = mock(TestComponent.class);
    when(mockedComponentFactory.make(mockedTestModule)).thenReturn(mockedTestComponent);
    tested = new DaggerComponentManager(mockedComponentFactory);
  }

  @Test
  public void should_handle_has_component() throws Exception {
    assertThat(tested.hasComponent(TestComponent.class)).isFalse();

    tested.makeComponent(TestComponent.class, mockedTestModule);

    assertThat(tested.hasComponent(TestComponent.class)).isTrue();
  }

  @Test
  public void should_destroy_component() throws Exception {
    tested.makeComponent(TestComponent.class, mockedTestModule);
    tested.destroyComponent(TestComponent.class);
    assertThat(tested.hasComponent(TestComponent.class)).isFalse();
  }

  @Test
  public void should_throw_exception_when_no_component_created() throws Exception {
    try {
      tested.makeComponent(TestComponent.class, new Object());
      fail();
    } catch (Exception e) {
      assertThat(e).isInstanceOf(DaggerComponentManager.DaggerComponentException.class);
      assertThat(e.getMessage()).isEqualTo("Can't create component "
          + TestComponent.class.getName()
          + " with given module "
          + Object.class.getName());
    }
  }

  @Test
  public void should_throw_exception_when_request_for_not_created_component() throws Exception {
    try {
      tested.getComponent(TestComponent.class);
      fail();
    } catch (Exception e) {
      assertThat(e).isInstanceOf(DaggerComponentManager.DaggerComponentException.class);
      assertThat(e.getMessage()).isEqualTo("Component "
          + TestComponent.class.getName()
          + " not found. Please call "
          + DaggerComponentManager.class.getName()
          + ".make() first");
    }
  }

  @Test
  public void should_return_created_component() throws Exception {
    tested.makeComponent(TestComponent.class, mockedTestModule);
    assertThat(tested.getComponent(TestComponent.class)).isEqualTo(mockedTestComponent);
  }

  interface TestComponent {

  }

  private static class TestModule {

  }
}